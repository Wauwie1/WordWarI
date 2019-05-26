package com.wordwargroup.wordwarserver.WS;

import Models.Player;

import java.util.ArrayList;

public class GameServer {
    private ArrayList<ServerLobby> lobbies = new ArrayList<>();

    public GameServer() {
        lobbies.add(new ServerLobby());
    }

    public ServerLobby findMockMatch(Player player) {
        ServerLobby lobby = lobbies.get(0);
        lobby.addPlayer(player);
        return lobby;
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
