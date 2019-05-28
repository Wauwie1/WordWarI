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

    private ClientGameController gameController;

    public void setGameController(ClientGameController controller) {
        this.gameController = controller;
        this.gameController.setStompSessionHandler(this);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("New session established : " + session.getSessionId());
        this.gameController.session = session;
        this.gameController.searchGame();

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
        IResponse message = (Response) payload;
        gameController.handleMessage(message);
    }
}
