package Client.GUIControllers;

import Client.ClientGameController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameGUIController {

    private ClientGameController gameController;

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


}
