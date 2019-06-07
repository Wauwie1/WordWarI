import Client.GUIControllers.LoaderFactory;
import Client.GUIControllers.Scenes;
import javafx.fxml.FXMLLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoaderFactoryTest {

    LoaderFactory factory;
    String filePath = "/C:/Users/Paul_Laptop/Documents/Fontys/S3/WordWarI/WordWarClient/target/classes/";
    @BeforeEach
    void setUp() {
        factory = new LoaderFactory();
    }

    @Test
    public void getLoaderTest1() {
        FXMLLoader loaderLogin = factory.getLoader(Scenes.LOGINSCENE);
        String expected = filePath + "login.fxml";
        assertEquals(expected, loaderLogin.getLocation().getFile());
    }

    @Test
    public void getLoaderTest2() {
        FXMLLoader loaderLobby = factory.getLoader(Scenes.LOBBYSCENE);
        String expected = filePath + "lobby.fxml";
        assertEquals(expected, loaderLobby.getLocation().getFile());
    }

    @Test
    public void getLoaderTest3() {
        FXMLLoader loaderGame = factory.getLoader(Scenes.GAMESCENE);
        String expected = filePath + "game.fxml";
        assertEquals(expected, loaderGame.getLocation().getFile());
    }

    @Test
    public void getLoaderTest4() {
        FXMLLoader loaderGame = factory.getLoader(null);
        assertNull(loaderGame);
    }

    @Test
    public void getLoaderTest5() {
        FXMLLoader loaderGame = factory.getLoader(Scenes.UNKNOWN);
        assertNull(loaderGame);
    }
}
