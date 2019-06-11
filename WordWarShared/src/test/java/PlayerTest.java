import Models.Player;
import Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    Player player;
    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    public void playerGiveWordTest() {
        String word = "Table";
        player.giveNewWord(word);
        assertEquals(word, player.getCurrentWord());
        assertEquals("", player.getTypedChars());
    }

    @Test
    public void playerLivesTest() {
        player.setLives(100);
        player.removeLife(10);

        assertEquals(100 - 10, player.getLives());
    }

    @Test
    public void playerTypeTest() {
        player.giveNewWord("CHAIR");
        player.setTypedChars("CHA");
        player.typeCharacter('I');
        assertEquals("CHAI", player.getTypedChars());
    }

    @Test
    public void playerCompletedTest() {
        player.giveNewWord("PLATE");
        player.setTypedChars("PLAT");
        assertEquals(false, player.completedWord());
        player.typeCharacter('E');
        assertEquals(true, player.completedWord());
    }

    @Test
    public void playerUserTest() {
        User user = new User();
        player.setUser(user);
        assertEquals(user, player.getUser());
    }
}
