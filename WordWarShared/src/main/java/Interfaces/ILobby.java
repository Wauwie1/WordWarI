package Interfaces;

import Models.GameState;
import Models.Player;

import java.util.ArrayList;

public interface ILobby {
     int getId();

     void setId(int id);

     ArrayList<Player> getPlayers();

     void setPlayers(ArrayList<Player> players);

     GameState getState();

     void setState(GameState state);
}
