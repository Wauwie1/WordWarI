package Client.Logic;

import Actions.ClientToServer;
import Client.GUIControllers.LobbyController;
import Client.GUIControllers.Scenes;
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

    @Setter private StompSession session;
    @Setter @Getter private StompSessionHandler stompSessionHandler;
    private ClientMessageHandler messageHandler;

    // UI Controller
    @Setter @Getter private UIController uiController = new UIController();


    // Game variables
    @Getter @Setter private User user;
    @Getter @Setter private ClientLobby lobby;

    public ClientLogic() {
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

    public void endGame(Player winner, Player loser) {
        IRequest request = new Request();
        request.setAction(ClientToServer.END_GAME);
        session.send("/app/play/" + lobby.getId(), request);
        session.disconnect();

        uiController.endGame(winner, loser);
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

    public void goToLobby() {
        LobbyController controller = null;
        try {
            controller = (LobbyController)uiController.goToScene(Scenes.LOBBYSCENE);
            controller.setUser(user);
            controller.setLogic(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(User user) {
        this.user = user;
        goToLobby();
    }

    public void connect() {
        String URL = "ws://localhost:8081/wordwarone";
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompClient.connect(URL, stompSessionHandler);
    }
}
