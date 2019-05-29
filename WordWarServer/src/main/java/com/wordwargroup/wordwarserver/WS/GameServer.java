package com.wordwargroup.wordwarserver.WS;

import Actions.ServerToClient;
import Models.LetterTyped;
import Models.Player;
import Models.User;
import Requests.IRequest;
import Requests.Request;
import Responses.IResponse;
import Responses.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordwargroup.wordwarserver.REST.Repositories.IDatabase;
import com.wordwargroup.wordwarserver.REST.Repositories.MySQLRepository;
import org.apache.catalina.Server;

import java.util.ArrayList;
import java.util.Random;

public class GameServer {
    private final ObjectMapper mapper = new ObjectMapper();
    private static final IDatabase database = new MySQLRepository();
    private ArrayList<ServerLobby> lobbies = new ArrayList<>();


    public GameServer() {
        lobbies.add(new ServerLobby());
    }

    public Response findMockMatch(User user) {
        Player player = createPlayer(user);

        ServerLobby lobby = lobbies.get(0);
        lobby.addPlayer(player);

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

    public Response letterTyped(Request message){
        LetterTyped letterTypedMessage =  mapper.convertValue(message.getData(), LetterTyped.class) ;
        int lobbyId = Integer.parseInt(letterTypedMessage.getLobbyId());
        letterTypedMessage.setLobbyId(String.valueOf(lobbyId));
        char letter = letterTypedMessage.getLetter().charAt(0);
        int playerId = letterTypedMessage.getPlayer().getUser().getId();



        ServerLobby serverLobby = null;
        for (ServerLobby lobby: lobbies) {
            if(lobby.getId() == lobbyId){
                serverLobby = lobby;
            }
        }

        for (Player player: serverLobby.getPlayers()) {
            if(player.getUser().getId() == playerId) {
                player.typeCharacter(letter);
                letterTypedMessage.setPlayer(player);
            }
        }

        Response response = new Response();
        response.setAction(ServerToClient.LETTER_TYPED);
        response.setData(letterTypedMessage);

        return response;
    }

    // TODO: Fix function
    public int findMatch(Player player) {
        boolean isPlaced = false;

        if(lobbies.size() == 0){
            ServerLobby lobby = new ServerLobby();
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
        giveNewWord(player);
        return player;
    }

    private void giveNewWord(Player player) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(1000);
        String word = database.getWord(randomIndex).toUpperCase();

        System.out.println(word);
        player.giveNewWord(word);
    }
}
