package LogicTests;

import Models.GameState;
import Models.Player;
import Models.User;
import com.wordwargroup.wordwarserver.REST.Repositories.MockDatabaseRepository;
import com.wordwargroup.wordwarserver.WS.Logic.ServerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServerLobbyTest {

    private ServerLobby lobby;

    @BeforeEach
    public void setup() {
        lobby = new ServerLobby(new MockDatabaseRepository());
    }

    @Test
    public void addPlayerTest() {
        assertEquals(0, lobby.getPlayers().size());
        Player player = new Player();
        lobby.addPlayer(player);
        assertEquals(1, lobby.getPlayers().size());
    }

    @Test
    public void startGameTest() {
        assertEquals(GameState.NOT_STARTED, lobby.getState());
        Player player = new Player();
        Player player2 = new Player();
        lobby.addPlayer(player);
        lobby.addPlayer(player2);
        assertEquals(GameState.IN_PROGRESS, lobby.getState());
    }

    @Test
    public void isFullTest() {
        assertEquals(false, lobby.isFull());
        Player player = new Player();
        lobby.addPlayer(player);
        assertEquals(false, lobby.isFull());
        Player player2 = new Player();
        lobby.addPlayer(player2);
        assertEquals(true, lobby.isFull());
    }

    @Test
    public void getNextWord() {
        assertEquals("Television", lobby.getNextWord("Telephone"));
    }
    @Test
    public void getNextWord2() {
        assertNull(lobby.getNextWord("UnknownWord"));
    }

    @Test
    public void getPlayer() throws Exception {
        Player player = new Player();
        User user = new User();
        user.setId(20);
        player.setUser(user);
        lobby.addPlayer(player);

        Player retrievedPlayer = lobby.getPlayer(20);
        assertEquals(retrievedPlayer, player);
    }

    @Test
    public void getPlayer2() {
        Player player = new Player();
        User user = new User();
        user.setId(20);
        player.setUser(user);
        lobby.addPlayer(player);

        assertThrows(Exception.class, () -> {
            lobby.getPlayer(99999);
        });
    }

    @Test
    public void getOpponentTest() throws Exception {
        Player player = new Player();
        User user = new User();
        user.setId(1);
        player.setUser(user);
        lobby.addPlayer(player);

        Player opponent = new Player();
        User opponentUser = new User();
        opponentUser.setId(2);
        opponent.setUser(opponentUser);
        lobby.addPlayer(opponent);

        assertEquals(opponent, lobby.getOpponent(1));
    }

    @Test
    public void getOpponentTest2() {
        Player player = new Player();
        User user = new User();
        user.setId(1);
        player.setUser(user);
        lobby.addPlayer(player);


        assertThrows(Exception.class, () -> {
            lobby.getOpponent(1);
        });
    }
}
