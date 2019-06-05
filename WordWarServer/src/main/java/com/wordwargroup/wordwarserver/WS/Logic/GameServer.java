package com.wordwargroup.wordwarserver.WS.Logic;

import Actions.ServerToClient;
import Models.LetterTyped;
import Models.Player;
import Models.User;
import Requests.Request;
import Responses.EndGameResponse;
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

    public Response letterTyped(Request message){

        // Read values from request
        LetterTyped letterTypedMessage =  mapper.convertValue(message.getData(), LetterTyped.class) ;
        int lobbyId = Integer.parseInt(letterTypedMessage.getLobbyId());
        letterTypedMessage.setLobbyId(String.valueOf(lobbyId));
        char letter = letterTypedMessage.getLetter().charAt(0);
        int playerId = letterTypedMessage.getPlayer().getUser().getId();
        Player messagePlayer = null;
        Player messagePlayerOpponent = null;


        ServerLobby serverLobby = null;
        for (ServerLobby lobby: lobbies) {
            if(lobby.getId() == lobbyId){
                serverLobby = lobby;
            }
        }

        for (Player player: serverLobby.getPlayers()) {
            if(player.getUser().getId() == playerId) {
                messagePlayer = player;
            }else {
                messagePlayerOpponent = player;
            }
        }

        Response response = new Response();

        messagePlayer.typeCharacter(letter);
        if(messagePlayer.completedWord()) {
            giveNewWord(messagePlayer, serverLobby);
            messagePlayerOpponent.removeLife(10);
            if(messagePlayerOpponent.getLives() <= 0) {
                EndGameResponse endGameResponse = new EndGameResponse();
                endGameResponse.setLoser(messagePlayerOpponent);
                endGameResponse.setWinner(messagePlayer);
                response.setAction(ServerToClient.GAME_OVER);
                response.setData(endGameResponse);
                serverLobby.reset();
                return response;
            } else {
                response.setAction(ServerToClient.NEW_WORD);
            }

        } else {
            response.setAction(ServerToClient.LETTER_TYPED);
        }


        letterTypedMessage.setPlayer(messagePlayer);
        letterTypedMessage.setPlayerOpponent(messagePlayerOpponent);



        response.setData(letterTypedMessage);

        return response;
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

    private void giveNewWord(Player player, ServerLobby lobby) {
        String word = null;
        try {
            word = lobby.getNextWord(player.getCurrentWord());

        }catch (NullPointerException exception) {
            exception.printStackTrace();
        }

        System.out.println(word);
        player.giveNewWord(word);
    }
}
