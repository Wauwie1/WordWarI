import Models.LetterTyped;
import Models.Player;
import Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelsTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    public void userTest() {
        User user = new User();
        user.setId(10);
        user.setUsername("Jan98");
        assertEquals(10, user.getId());
        assertEquals("Jan98", user.getUsername());
    }

    @Test
    public void letterTypedTest() {
        LetterTyped typed = new LetterTyped();
        Player player = new Player();
        Player opponent = new Player();
        String letter = "K";
        String lobbyId = "20";
        typed.setPlayer(player);
        typed.setPlayerOpponent(opponent);
        typed.setLetter(letter);
        typed.setLobbyId(lobbyId);

        assertEquals(player, typed.getPlayer());
        assertEquals(opponent, typed.getPlayerOpponent());
        assertEquals(letter, typed.getLetter());
        assertEquals(lobbyId, typed.getLobbyId());
    }
}
