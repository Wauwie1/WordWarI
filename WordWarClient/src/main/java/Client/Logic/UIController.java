package Client.Logic;

import Client.GUIControllers.*;
import Client.Interfaces.IGUIController;
import Models.Player;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.io.IOException;

@Log4j
public class UIController {

    private LoaderFactory loaderFactory = new LoaderFactory();
    @Setter private Stage stage;
    @Setter private LoginController loginController;
    @Setter private LobbyController lobbyController;
    @Setter private GameGUIController gameGUIController;
    @Setter private ClientLogic logic;

    public IGUIController goToScene(Scenes scene) throws IOException {
        // Create lobby scene
        FXMLLoader loader = loaderFactory.getLoader(scene);
        Parent gameParent = loader.load();
        Scene gameScene = new Scene(gameParent, 900, 500);

        IGUIController controller = loader.getController();

        if(scene == Scenes.GAMESCENE) {
            ((GameGUIController) controller).setScene(gameScene);
        }

        // Display lobby scene
        Platform.runLater(() -> {
            stage.setScene(gameScene);
            stage.show();
        });

        return controller;


    }

    public void endGame(Player winner) {
        this.gameGUIController.endGame(winner);
    }

    public void onGameFound() {
        try {
            gameGUIController = (GameGUIController) goToScene(Scenes.GAMESCENE);
            gameGUIController.setKeyListener();
            gameGUIController.setLogic(logic);
            gameGUIController.createGameField();
        } catch (IOException e) {
            log.error(e);
        }

    }

    public void newWord(Player playerNewWord, Player playerOpponentNewWord) {
        gameGUIController.newWord(playerNewWord, playerOpponentNewWord);
    }

    public void charTyped(Player player) {
        gameGUIController.charTyped(player);
    }
}
