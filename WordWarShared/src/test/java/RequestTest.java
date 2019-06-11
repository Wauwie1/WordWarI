import Actions.ClientToServer;
import Requests.IRequest;
import Requests.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestTest {

    IRequest request;

    @BeforeEach
    void setUp() {
        request = new Request();
    }

    @Test
    public void requestTest() {
        request.setAction(ClientToServer.LETTER_TYPED);
        request.setData("I am data");
        assertEquals(ClientToServer.LETTER_TYPED, request.getAction());
        assertEquals("I am data", request.getData());
    }
}
