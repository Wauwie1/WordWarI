package Client.Logic;

import Client.Interfaces.IStompSessionHandler;
import Responses.IResponse;
import Responses.Response;
import lombok.extern.log4j.Log4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Log4j
public class StompSessionHandler extends StompSessionHandlerAdapter implements IStompSessionHandler {

    private ClientMessageHandler messageHandler;


    public void setMessageHandler(ClientMessageHandler handler) {
        this.messageHandler = handler;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("New session established : " + session.getSessionId());
        this.messageHandler.setSession(session);
        this.messageHandler.searchGame();
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("Got an exception");
        log.error(exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Response.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        IResponse message = (Response) payload;
        messageHandler.handleMessage(message);
    }
}
