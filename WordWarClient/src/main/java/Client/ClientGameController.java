package Client;

import Actions.ClientToServer;
import Client.GUIControllers.GameGUIController;
import Client.GUIControllers.LobbyController;
import Client.GUIControllers.LoginController;
import Models.User;
import Requests.IRequest;
import Requests.Request;
import Responses.IResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.messaging.simp.stomp.StompSession;

public class ClientGameController {

    // WS
    private ObjectMapper mapper = new ObjectMapper();
    @Setter private StompSessionHandler stompSessionHandler;
    @Setter private StompSession session;

    // GUI Controllers TODO: Make seperate class?
    @Setter private Stage stage;
    @Setter private LoginController loginController;
    @Setter private LobbyController lobbyController;
    @Setter private GameGUIController gameGUIController;

    // Game variables
    @Getter @Setter private User user;
    @Getter @Setter private ClientLobby lobby;


    public void handleMessage(IResponse message) {
        switch (message.getAction()){
            case SEARCHING:
                System.out.println("Searching for opponent...");
                return;
            case GAME_FOUND:
                lobby = mapper.convertValue(message.getData(), ClientLobby.class);

                System.out.println("Opponent found: " + lobby.getId());
                gameFound();
                session.subscribe("/topic/game/" + lobby.getId(), stompSessionHandler);
                System.out.println("Subscribed to /topic/game");
                return;
            default:
                System.out.println("Received unknown action.");
                return;
        }
    }

    private void gameFound() {
        try {
            lobbyController.onGameFound(stage);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchGame() {
        // Change subscription
        session.subscribe("/topic/findmatch", stompSessionHandler);
        System.out.println("Subscribed to /topic/findmatch");

        // Build request
        IRequest request = new Request();
        request.setAction(ClientToServer.SEARCH_GAME);
        request.setData(user);

        // Send Request
        session.send("/app/find", request);
        System.out.println("Message sent to websocket server");
    }

    public void sendKeyPress(KeyCode code) {
        String key = code.toString();
        IRequest request = new Request();
        request.setAction(ClientToServer.LETTER_TYPED);
        request.setData(key);
        session.send("/app/play/" + lobby.getId(), request);
    }
}
