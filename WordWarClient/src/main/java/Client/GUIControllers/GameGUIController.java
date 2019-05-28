package Client.GUIControllers;

import Client.ClientGameController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

public class GameGUIController {

    private ClientGameController gameController;
    private Scene scene;

    @FXML public Label Label_Word;
    @FXML public Label Label_Word_Typed;
    @FXML public Label Label_Opponent_Word;
    @FXML public Label Label_Opponent_Word_Typed;



    public ClientGameController getGameController() {
        return gameController;
    }

    public void setGameController(ClientGameController gameController) {
        this.gameController = gameController;
    }

    public void initialize(){

    }


    public void setKeyListener(Scene scene) {
        this.scene = scene;
        this.scene.setOnKeyPressed(e -> {
            gameController.sendKeyPress(e.getCode());

        });
    }


}
