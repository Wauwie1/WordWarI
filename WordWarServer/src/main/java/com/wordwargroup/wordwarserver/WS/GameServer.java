package com.wordwargroup.wordwarserver.WS;

import Actions.ServerToClient;
import Models.Player;
import Models.User;
import Responses.Response;

import java.util.ArrayList;

public class GameServer {
    private ArrayList<ServerLobby> lobbies = new ArrayList<>();

    public GameServer() {
        lobbies.add(new ServerLobby());
    }

    public Response findMockMatch(User user) {
        Player player = new Player(user, 100);
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
}
