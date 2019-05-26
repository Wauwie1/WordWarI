package Client;

import Actions.ClientToServer;
import Actions.ServerToClient;
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
    private Request getSampleMessage() {
        Request request = new Request();
        request.setAction(ClientToServer.SEARCH_GAME);
        request.setData(user);
        return request;
    }
}
