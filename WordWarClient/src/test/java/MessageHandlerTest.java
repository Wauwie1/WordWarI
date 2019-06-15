import Actions.ServerToClient;
import Client.Logic.ClientMessageHandler;
import Responses.IResponse;
import Responses.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageHandlerTest {

    ClientMessageHandler handler;
    IResponse response;
    @BeforeEach
    void setUp() {
        handler = new ClientMessageHandler();
        response = new Response();
    }

    @Test
    public void handlerNullTest() {
        assertThrows(NullPointerException.class, () -> {
            handler.handleMessage(null);
        });
    }

    @Test
    public void handlerSearchingTest() {
        response.setAction(ServerToClient.SEARCHING);
        assertDoesNotThrow(() -> {
            handler.handleMessage(response);
        });
    }

    @Test
    public void handlerGameFoundTest() {
        response.setAction(ServerToClient.GAME_FOUND);
        response.setData("Invalid data");
        assertThrows(IllegalArgumentException.class, () -> {
            handler.handleMessage(response);
        });
    }

    @Test
    public void handlerLetterTypedTest() {
        response.setAction(ServerToClient.LETTER_TYPED);
        response.setData("Invalid data");
        assertThrows(IllegalArgumentException.class, () -> {
            handler.handleMessage(response);
        });
    }

    @Test
    public void handlerNewWordTest() {
        response.setAction(ServerToClient.NEW_WORD);
        response.setData("Invalid data");
        assertThrows(IllegalArgumentException.class, () -> {
            handler.handleMessage(response);
        });
    }

    @Test
    public void handlerGameOverTest() {
        response.setAction(ServerToClient.GAME_OVER);
        response.setData("Invalid data");
        assertThrows(IllegalArgumentException.class, () -> {
            handler.handleMessage(response);
        });
    }
}
