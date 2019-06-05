package com.wordwargroup.wordwarserver.WS.Logic;

import Interfaces.ILobby;
import Models.GameState;
import Models.Player;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordwargroup.wordwarserver.REST.Repositories.IDatabase;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerLobby implements ILobby {

    @Getter @Setter private int id;
    @Getter @Setter private List<Player> players = new ArrayList<>();
    private List<String> words;
    private IDatabase database;
    @Getter @Setter private GameState state = GameState.NOT_STARTED;

    public ServerLobby(IDatabase database) {
        this.database = database;
        reset();
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

    private void setRandomId() {
        Random rand = new Random();
        this.id = rand.nextInt(999);
    }

    public void reset() {
        setRandomId();
        words = database.getWords();
        players = new ArrayList<>();
    }

    public Player getPlayer(int playerId) throws Exception {
        for (Player player: players) {
            if(player.getUser().getId() == playerId) {
                return player;
            }
        }

        throw new Exception("No player found in lobby: " + id + ", with id: " + playerId);
    }

    public Player getOpponent(int playerId) throws Exception {
        for (Player player: players) {
            if(player.getUser().getId() != playerId) {
                return player;
            }
        }

        throw new Exception("No opponent found in lobby: " + id + ", with id: " + playerId);
    }
}
