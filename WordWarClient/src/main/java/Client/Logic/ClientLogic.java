package Client.Logic;

import Actions.ClientToServer;
import Client.GUIControllers.LobbyController;
import Client.GUIControllers.LoginController;
import Client.GUIControllers.RegisterController;
import Client.GUIControllers.Scenes;
import Client.Interfaces.ILoginRepository;
import Client.Repositories.LoginRepository;
import Models.LetterTyped;
import Models.Player;
import Models.User;
import Requests.IRequest;
import Requests.Request;
import lombok.Getter;
import lombok.Setter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.IOException;

public class ClientLogic {

    private ILoginRepository loginRepository;
    @Setter private StompSession session;
    @Setter @Getter private StompSessionHandler stompSessionHandler;
    @Getter private ClientMessageHandler messageHandler;

    // UI Controller
    @Setter @Getter private UIController uiController = new UIController();


    // Game variables
    @Getter @Setter private User user;
    @Getter @Setter private ClientLobby lobby;

    public ClientLogic() {
        loginRepository = new LoginRepository();
        messageHandler = new ClientMessageHandler();
        messageHandler.setLogic(this);
        stompSessionHandler = new StompSessionHandler();
        stompSessionHandler.setMessageHandler(messageHandler);
        uiController.setLogic(this);

    }

    public void foundGame(ClientLobby lobby) {
        this.lobby = lobby;
        System.out.println("Opponent found: " + lobby.getId());
        uiController.onGameFound();
        session.subscribe("/topic/game/" + lobby.getId(), stompSessionHandler);
        System.out.println("Subscribed to /topic/game");
    }

    public void letterTyped(Player player) {
        uiController.charTyped(player);
    }

    public void newWord(Player playerNewWord, Player playerOpponentNewWord) {
        uiController.newWord(playerNewWord, playerOpponentNewWord);
    }

    public void endGame(Player winner) {
        IRequest request = new Request();
        request.setAction(ClientToServer.END_GAME);
        session.send("/app/play/" + lobby.getId(), request);
        session.disconnect();

        uiController.endGame(winner);
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

    public void sendKeyPress(String letter, Player player) {
        letter = letter.toUpperCase();
        IRequest request = new Request();
        request.setAction(ClientToServer.LETTER_TYPED);

        LetterTyped letterTyped = new LetterTyped();
        letterTyped.setLetter(letter);
        letterTyped.setPlayer(player);
        letterTyped.setLobbyId(String.valueOf(lobby.getId()));


        request.setData(letterTyped);
        session.send("/app/play/" + lobby.getId(), request);
    }


    public boolean login(String username, String password) {
        User user = loginRepository.login(username, password);

        if(user != null) {
            this.user = user;
            goToLobby();
            return true;
        }else {
            return false;
        }
    }

    public void connect() {
        String URL = "ws://localhost:8081/wordwarone";
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompClient.connect(URL, stompSessionHandler);
    }


    public void goToLobby() {
        LobbyController controller;
        try {
            controller = (LobbyController)uiController.goToScene(Scenes.LOBBYSCENE);
            controller.setUser(user);
            controller.setLogic(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToRegister() {
        RegisterController controller;
        try {
            controller = (RegisterController)uiController.goToScene(Scenes.REGISTERSCENE);
            controller.setLogic(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToLogin() {
        LoginController controller;
        try {
            controller = (LoginController) uiController.goToScene(Scenes.LOGINSCENE);
            controller.setLogic(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean register(String username, String password) {
        return loginRepository.register(username, password);
    }
}
