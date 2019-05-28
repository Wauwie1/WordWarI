package com.wordwargroup.wordwarserver.WS;

import Interfaces.ILobby;
import Models.GameState;
import Models.Player;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerLobby implements ILobby {

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

    @Override
    public void setId(int id) {

    }

    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public void setPlayers(List<Player> players) {

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
            return true;
        }else {
            return false;
        }
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
