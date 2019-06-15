package LogicTests;

import Actions.ServerToClient;
import Models.User;
import Responses.Response;
import com.wordwargroup.wordwarserver.REST.Repositories.MockDatabaseRepository;
import com.wordwargroup.wordwarserver.WS.Logic.GameServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class GameServerTest {

    GameServer server = new GameServer(new MockDatabaseRepository());

    @BeforeEach
    public void setup() {

    }

    @Test
    public void findMatchTest() {
        User user = new User();
        user.setId(10);
        user.setUsername("Foo");

        Response response = server.findMockMatch(user);
        assertEquals(ServerToClient.SEARCHING, response.getAction());
    }
}
