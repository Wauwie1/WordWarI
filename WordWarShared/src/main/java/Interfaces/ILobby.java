package Interfaces;

import Models.GameState;
import Models.Player;
import java.util.List;

public interface ILobby {
     int getId();
     void setId(int id);

     List<Player> getPlayers();

     void setPlayers(List<Player> players);

     GameState getState();

     void setState(GameState state);
}
