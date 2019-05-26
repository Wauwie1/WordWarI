package Client;

import Actions.ClientToServer;
import Actions.ServerToClient;
import Models.User;
import Requests.FindMatchRequest;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private User user = new User();

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("New session established : " + session.getSessionId());
        session.subscribe("/topic/findmatch", this);
        System.out.println("Subscribed to /topic/findmatch");
        session.send("/app/find", getSampleMessage());
        System.out.println("Message sent to websocket server");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("Got an exception");
        System.out.println(exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return ServerToClient.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        ServerToClient msg = (ServerToClient) payload;
        System.out.println(msg);
    }

    /**
     * A sample message instance.
     * @return instance of <code>Message</code>
     */
    private FindMatchRequest getSampleMessage() {
        FindMatchRequest msg = new FindMatchRequest();
        msg.setAction(ClientToServer.SEARCH_GAME);
        msg.setUser(user);
        return msg;
    }
}
