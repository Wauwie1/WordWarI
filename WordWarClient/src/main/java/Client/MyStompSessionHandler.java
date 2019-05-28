package Client;

import Actions.ClientToServer;
import Actions.ServerToClient;
import Client.GUIControllers.GameGUIController;
import Client.GUIControllers.LobbyController;
import Client.GUIControllers.LoginController;
import Models.User;
import Requests.FindMatchData;
import Requests.Request;
import Responses.IResponse;
import Responses.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private User user = new User();
    private ClientGameController gameController;

    private LoginController loginController;
    private LobbyController lobbyController;
    private GameGUIController gameGUIController;

    public void setUser(User user) {
        this.user = user;
    }
    public void setGameController(ClientGameController controller) { this.gameController = controller; }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("New session established : " + session.getSessionId());
        this.gameController.setSession(session);
        session.subscribe("/topic/findmatch", this);
        System.out.println("Subscribed to /topic/findmatch");
        session.send("/app/find", searchGame());
        System.out.println("Message sent to websocket server");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("Got an exception");
        System.out.println(exception);
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Response.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {

        IResponse msg = (Response) payload;
        ObjectMapper mapper = new ObjectMapper();
        switch (msg.getAction()){
            case SEARCHING:
                System.out.println("Searching for opponent...");
                return;
            case GAME_FOUND:
                ClientLobby lobby = mapper.convertValue(msg.getData(), ClientLobby.class) ;
                System.out.println("Opponent found: " + lobby.getId());
                gameController.gameFound(lobby);
                gameController.getSession().subscribe("/topic/game/20", this);
                System.out.println("Subscribed to /topic/game");
                return;
                default:
                    System.out.println("Received unknown action.");
                    return;

        }
    }

    /**
     * A sample message instance.
     * @return instance of <code>Message</code>
     */
    private Request searchGame() {
        Request request = new Request();
        request.setAction(ClientToServer.SEARCH_GAME);
        request.setData(user);
        return request;
    }

}
