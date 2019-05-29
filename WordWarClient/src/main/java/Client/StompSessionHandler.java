package Client;

import Responses.IResponse;
import Responses.Response;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class StompSessionHandler extends StompSessionHandlerAdapter {

    private ClientGameController gameController;

    public void setGameController(ClientGameController controller) {
        this.gameController = controller;
        this.gameController.setStompSessionHandler(this);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("New session established : " + session.getSessionId());
        this.gameController.setSession(session);
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
