package com.wordwargroup.wordwarserver.WS.Logic;

import Actions.ServerToClient;
import Models.LetterTyped;
import Models.Player;
import Models.User;
import Requests.Request;
import Responses.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordwargroup.wordwarserver.REST.Repositories.IDatabase;
import com.wordwargroup.wordwarserver.REST.Repositories.MySQLRepository;


import java.util.ArrayList;

public class GameServer {
    private final ObjectMapper mapper = new ObjectMapper();
    private final IDatabase database = new MySQLRepository();
    private ArrayList<ServerLobby> lobbies = new ArrayList<>();

    public GameServer() {
        lobbies.add(new ServerLobby(database));
    }

    public Response findMockMatch(User user) {
        Player player = createPlayer(user);


        ServerLobby lobby = lobbies.get(0);
        lobby.addPlayer(player);
        player.giveNewWord(lobby.getInitialWord());

        Response response = new Response();
        if(lobby.isFull()){
            System.out.println("Opponent found");
            response.setAction(ServerToClient.GAME_FOUND);
        }else {
            System.out.println("Searching...");
            response.setAction(ServerToClient.SEARCHING);

        }
        response.setData(lobby);

        return response;
    }

    public Response letterTyped(Request message, String id) throws Exception {

        // Read values from request
        LetterTyped letterTypedMessage =  mapper.convertValue(message.getData(), LetterTyped.class) ;
        int lobbyId = Integer.parseInt(id);
        char letter = letterTypedMessage.getLetter().charAt(0);
        User user = letterTypedMessage.getPlayer().getUser();
        int playerId = user.getId();

        // Find the correct lobby
        ServerLobby lobby = findInLobbies(lobbyId);

        // Assign players
        Player player = lobby.getPlayer(playerId);
        Player opponent = lobby.getOpponent(playerId);


        // Generates response
        TypeCharacterLogic typeCharacterLogic = new TypeCharacterLogic(player, opponent, lobby);
        Response response = typeCharacterLogic.typeCharacter(letter);

        if(response.getAction() != ServerToClient.GAME_OVER){
            // Sets the perspective of the response
            letterTypedMessage.setPlayer(player);
            letterTypedMessage.setPlayerOpponent(opponent);

            response.setData(letterTypedMessage);
        }

        return response;

    }

    private ServerLobby findInLobbies(int lobbyId) throws Exception {
        for (ServerLobby lobby: lobbies) {
            if(lobby.getId() == lobbyId){
                return lobby;
            }
        }
        throw new Exception("No lobby found with lobbyId: " + lobbyId);
    }

    // TODO: Fix function
    public int findMatch(Player player) {
        boolean isPlaced = false;

        if(lobbies.size() == 0){
            ServerLobby lobby = new ServerLobby(database);
            lobby.addPlayer(player);
            isPlaced = true;
            return lobby.getId();
        }

        for (ServerLobby lobby : lobbies) {
                if(!isPlaced) {
                    if (!lobby.isFull()) {
                        lobby.addPlayer(player);
                        isPlaced = true;
                        return lobby.getId();
                    }
                }
            }
        return 0;
    }

    private Player createPlayer(User user) {
        Player player = new Player();

        player.setUser(user);
        player.setLives(100);

        return player;
    }

    public void endGame(String id) throws Exception {
        int lobbyId = Integer.valueOf(id);
        ServerLobby lobby = findInLobbies(lobbyId);
        lobby.reset();
    }
}
