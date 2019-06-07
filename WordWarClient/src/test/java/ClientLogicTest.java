import Client.Logic.ClientLobby;
import Client.Logic.ClientLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientLogicTest {

    private ClientLogic logic;
    @BeforeEach
    void setUp() {
        logic = new ClientLogic();
    }

    @Test
    void constructorTest() {
        assertNotNull(logic);
        assertNotNull(logic.getMessageHandler());
        assertNotNull(logic.getStompSessionHandler());
    }
}
