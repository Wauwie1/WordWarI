package Client;

import Actions.ClientToServer;
import Client.GUIControllers.GameGUIController;
import Client.GUIControllers.LobbyController;
import Client.GUIControllers.LoginController;
import Models.User;
import Requests.IRequest;
import Requests.Request;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.springframework.messaging.simp.stomp.StompSession;

public class ClientGameController {
    private StompSession session;
    private User user;
    private Stage stage;

    private LoginController loginController;
    private LobbyController lobbyController;
    private GameGUIController gameGUIController;

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    public void setGameGUIController(GameGUIController gameGUIController) {
        this.gameGUIController = gameGUIController;
    }

    public StompSession getSession() {
        return session;
    }

    public void setSession(StompSession session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void gameFound(ClientLobby lobby) {
        try {
            lobbyController.onGameFound(stage);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendKeyPress(KeyCode code) {
        String key = code.toString();
        IRequest request = new Request();
        request.setAction(ClientToServer.LETTER_TYPED);
        ((Request) request).setData(key);
        session.send("/app/play/20", request);
    }
}