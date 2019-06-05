package Client.Interfaces;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

import java.lang.reflect.Type;

public interface IStompSessionHandler {
    void afterConnected(StompSession session, StompHeaders connectedHeaders);
    void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception);
    Type getPayloadType(StompHeaders headers);
    void handleFrame(StompHeaders headers, Object payload);
}
