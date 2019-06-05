package Client;

import Actions.ClientToServer;
import Client.GUIControllers.LobbyController;
import Models.LetterTyped;
import Models.Player;
import Models.User;
import Requests.IRequest;
import Requests.Request;
import Responses.EndGameResponse;
import Responses.IResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.IOException;

public class ClientGameController {

    // WS
    private ObjectMapper mapper = new ObjectMapper();
    @Setter @Getter private StompSessionHandler stompSessionHandler;
    @Setter private StompSession session;

    // UI Controller
    private UIController uiController = new UIController(this);

    // Game variables
    @Getter @Setter private User user;
    @Getter @Setter private ClientLobby lobby;

    public ClientGameController() {
        stompSessionHandler = new StompSessionHandler();
        stompSessionHandler.setGameController(this);
    }

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
            case LETTER_TYPED:
                LetterTyped letterTypedMessage = mapper.convertValue(message.getData(), LetterTyped.class);
                Player player = letterTypedMessage.getPlayer();
                uiController.charTyped(player);
                return;
            case NEW_WORD:
                LetterTyped messageNewWord = mapper.convertValue(message.getData(), LetterTyped.class);
                Player playerNewWord = messageNewWord.getPlayer();
                Player playerOpponentNewWord = messageNewWord.getPlayerOpponent();
                uiController.newWord(playerNewWord, playerOpponentNewWord);
                return;
            case GAME_OVER:
                EndGameResponse endGameResponse = mapper.convertValue(message.getData(), EndGameResponse.class);
                Player winner = endGameResponse.getWinner();
                Player loser = endGameResponse.getLoser();
                endGame(winner, loser);
                return;
            default:
                System.out.println("Received unknown action.");
                return;
        }
    }

    private void endGame(Player winner, Player loser) {
        session.disconnect();

        uiController.endGame(winner, loser);
    }

    private void gameFound() {
        try {
            uiController.onGameFound();

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
        try {
            LobbyController controller = (LobbyController)uiController.goToScene(Scenes.LOBBYSCENE);
            controller.setUser(user);
            controller.setGameController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(User user) {
        this.user = user;
        LobbyController controller = null;
        try {
            controller = (LobbyController)uiController.goToScene(Scenes.LOBBYSCENE);
            controller.setUser(user);
            controller.setGameController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setStage(Stage stage) {
        uiController.setStage(stage);
    }

    public void connect() {
        String URL = "ws://localhost:8081/wordwarone";
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompClient.connect(URL, stompSessionHandler);    }
}
