import Actions.ServerToClient;
import Models.Player;
import Models.User;
import Responses.EndGameResponse;
import Responses.IResponse;
import Responses.Response;
import Responses.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseTest {

    IResponse response;

    @BeforeEach
    void setUp() {
        response = new Response();
    }

    @Test
    public void responseTest1() {
        response.setAction(ServerToClient.SEARCHING);
        response.setData("Test");

        assertEquals(ServerToClient.SEARCHING, response.getAction());
        assertEquals("Test", response.getData());
        assertEquals("Action = SEARCHING. Data: Test.", response.toString());
    }

    @Test
    public void responseTest2() {
        Player winner = new Player();
        Player loser = new Player();
        EndGameResponse endResponse = new EndGameResponse();
        endResponse.setWinner(winner);
        endResponse.setLoser(loser);

        assertEquals(winner, endResponse.getWinner());
        assertEquals(loser, endResponse.getLoser());
    }

    @Test
    public void responseTest3() {
        UserResponse userResponse = new UserResponse();
        userResponse.setStatus("200");
        User user = new User();
        userResponse.setValue(user);
        assertEquals("200", userResponse.getStatus());
        assertEquals(user, userResponse.getValue());
    }
}
