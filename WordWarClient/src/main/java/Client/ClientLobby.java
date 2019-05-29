package Client;

import Interfaces.ILobby;
import Models.GameState;
import Models.Player;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientLobby implements ILobby {

    @Getter @Setter private int id;
    @Getter @Setter private List<Player> players = new ArrayList();
    @Getter @Setter private GameState state = GameState.NOT_STARTED;

}
