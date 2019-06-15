package LogicTests;

import Actions.ServerToClient;
import Models.Player;
import Responses.EndGameResponse;
import Responses.Response;
import com.wordwargroup.wordwarserver.REST.Repositories.MockDatabaseRepository;
import com.wordwargroup.wordwarserver.WS.Logic.ServerLobby;
import com.wordwargroup.wordwarserver.WS.Logic.TypeCharacterLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TypeCharacterLogicTest {

    TypeCharacterLogic logic;
    Player player;
    Player opponent;
    @BeforeEach
    public void setup() {
        player = new Player();
        player.giveNewWord("Bedroom");
        player.setLives(100);

        opponent = new Player();
        opponent.setLives(100);

        ServerLobby lobby = new ServerLobby(new MockDatabaseRepository());

        logic = new TypeCharacterLogic(player, opponent, lobby);
    }

    @Test
    public void logicTest() {
        assertNotNull(logic);
    }

    @Test
    public void typeCharTest() {
        player.setTypedChars("Bedro");

        Response response = logic.typeCharacter('o');
        assertEquals(ServerToClient.LETTER_TYPED, response.getAction());
    }

    @Test
    public void typeCharTest2() {
        player.setTypedChars("Bedroo");
        Response response = logic.typeCharacter('m');
        assertEquals(ServerToClient.NEW_WORD, response.getAction());
        assertEquals(90, opponent.getLives());
    }

    @Test
    public void endGameTest() {
        player.setTypedChars("Bedroo");
        opponent.setLives(10);
        Response response = logic.typeCharacter('m');

        assertEquals(ServerToClient.GAME_OVER, response.getAction());
        assertEquals(0, opponent.getLives());

        EndGameResponse endgame = (EndGameResponse)response.getData();
        assertEquals(player, endgame.getWinner());
        assertEquals(opponent, endgame.getLoser());
    }
}
