package com.wordwargroup.wordwarserver.WS;

import Actions.ServerToClient;
import Models.Player;
import Models.User;
import Responses.Response;
import com.wordwargroup.wordwarserver.REST.Repositories.IDatabase;
import com.wordwargroup.wordwarserver.REST.Repositories.MySQLRepository;

import java.util.ArrayList;
import java.util.Random;

public class GameServer {
    private ArrayList<ServerLobby> lobbies = new ArrayList<>();
    private static final IDatabase database = new MySQLRepository();

    public GameServer() {
        lobbies.add(new ServerLobby());
    }

    public Response findMockMatch(User user) {
        Player player = new Player();
        player.setUser(user);
        player.setLives(100);
        giveNewWord(player);

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

    private void giveNewWord(Player player) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(1000);
        String word = database.getWord(randomIndex);
        System.out.println(word);
        player.giveNewWord(word);
    }
}
