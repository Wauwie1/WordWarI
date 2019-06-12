package LogicTests;

import Models.User;
import com.wordwargroup.wordwarserver.REST.Repositories.MockDatabaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

public class MockRepoTest {

    private MockDatabaseRepository database;

    @BeforeEach
    public void setup() {
        database = new MockDatabaseRepository();
    }

    @Test
    public void loginTest() {
        User user = database.login("Test123", "Wachtwoord123");
        assertNotNull(user);
    }

    @Test
    public void loginTest2() {
        User user = database.login("Wrong", "AlsoWrong");
        assertNull(user);
    }

    @Test
    public void loginTest3() {
        User user = database.login(null, null);
        assertNull(user);
    }

    @Test
    public void wordsTest() {
        List<String> words = database.getWords();
        assertNotNull(words);
        assertEquals(11, words.size());
    }
}
