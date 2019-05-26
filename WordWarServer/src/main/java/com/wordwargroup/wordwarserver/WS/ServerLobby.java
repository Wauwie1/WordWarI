package com.wordwargroup.wordwarserver.WS;

import Models.GameState;
import Models.Player;

import java.util.ArrayList;
import java.util.Random;

public class ServerLobby {

    private int id;
    private ArrayList<Player> players = new ArrayList<>();
    private GameState state = GameState.NOT_STARTED;

    public ServerLobby() {
        Random rand = new Random();
        id = rand.nextInt(500);
    }

    public int getId() {
        return id;
    }
    public void addPlayer(Player player) {
        players.add(player);
        if(players.size() == 2){
            startGame();
        }
    }

    private void startGame() {
        state = GameState.IN_PROGRESS;
        System.out.println("Game started.");
    }

    public void attackPlayer(Player player, int amount){
        int index = players.indexOf(player);
        Player attackedPlayer = players.get(index);
        attackedPlayer.removeLife(1);
    }

    public boolean isFull(){
        if(players.size() == 2){
            return false;
        }else {
            return true;
        }
    }

}
