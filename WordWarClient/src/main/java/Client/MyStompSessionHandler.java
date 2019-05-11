package Client;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {


    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("New session established : " + session.getSessionId());
        session.subscribe("/topic/greetings", this);
        System.out.println("Subscribed to /topic/greetings");
        session.send("/app/hello", getSampleMessage());
        System.out.println("Message sent to websocket server");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("Got an exception");
        System.out.println(exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Greeting.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Greeting msg = (Greeting) payload;
        System.out.println("Received : " + msg.getValue() + " from : " + msg.getType());
    }

    /**
     * A sample message instance.
     * @return instance of <code>Message</code>
     */
    private Greeting getSampleMessage() {
        Greeting msg = new Greeting();
        msg.setValue(new Value());
        msg.setType("Ok");
        return msg;
    }
}
