package com.wordwargroup.wordwarserver.WS;

import Interfaces.ILobby;
import Models.GameState;
import Models.Player;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordwargroup.wordwarserver.REST.Repositories.IDatabase;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerLobby implements ILobby {

    private int id;
    private List<Player> players = new ArrayList<>();
    private List<String> words;
    private IDatabase database;
    private GameState state = GameState.NOT_STARTED;

    public ServerLobby(IDatabase database) {
        this.database = database;
        words = database.getWords();
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

    public String getNextWord(String currentWord) {
        for (int i = 0; i < words.size(); i++) {
            if(words.get(i).compareTo(currentWord) == 0) {
                return words.get(i + 1);
            }
        }

        return null;
    }

    public String getInitialWord() {
        return words.get(0);
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public void setState(GameState state) {
        this.state = state;
    }


}
