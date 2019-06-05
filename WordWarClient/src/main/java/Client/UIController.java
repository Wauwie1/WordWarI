package Client;

import Client.GUIControllers.GameGUIController;
import Client.GUIControllers.LobbyController;
import Client.GUIControllers.LoginController;
import Client.Interfaces.IGUIController;
import Models.Player;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;

public class UIController {

    private LoaderFactory loaderFactory = new LoaderFactory();
    @Setter private Stage stage;
    @Setter private LoginController loginController;
    @Setter private LobbyController lobbyController;
    @Setter private GameGUIController gameGUIController;
    private ClientGameController gameController;

    public UIController(ClientGameController gameController) {
        this.gameController = gameController;
    }

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

    public void endGame(Player winner, Player loser) {
        this.gameGUIController.endGame(winner, loser);
    }

    public void onGameFound() {
        try {
            gameGUIController = (GameGUIController) goToScene(Scenes.GAMESCENE);
            gameGUIController.setKeyListener();
            gameGUIController.setGameController(gameController);
            gameGUIController.createGameField();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void newWord(Player playerNewWord, Player playerOpponentNewWord) {
        gameGUIController.newWord(playerNewWord, playerOpponentNewWord);
    }

    public void charTyped(Player player) {
        gameGUIController.charTyped(player);
    }
}
