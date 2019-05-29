package Client.GUIControllers;

import Client.ClientGameController;
import Client.ClientLobby;
import Models.Player;
import Models.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.util.List;

public class GameGUIController {

    @FXML public Label Label_Word;
    @FXML public Label Label_Word_Typed;
    @FXML public Label Label_Opponent_Word;
    @FXML public Label Label_Opponent_Word_Typed;
    @FXML public Label Label_Name_Player;
    @FXML public Label Label_Name_Opponent;

    private ClientGameController gameController;
    private Scene scene;

    private Player player;
    private Player opponent;

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
            System.out.println(e.getCode());
            gameController.sendKeyPress(e.getCode(), player);
        });
    }

    public void createGameField() {
        ClientLobby lobby = gameController.getLobby();
        User user = gameController.getUser();
        List<Player> players = lobby.getPlayers();

        for (Player player: players) {
            if(player.getUser().getId() == user.getId()) {
                this.player = player;
            }else {
                this.opponent = player;
            }
        }

        Label_Name_Player.setText(player.getUser().getUsername());
        Label_Name_Opponent.setText(opponent.getUser().getUsername());
        setNewWordPlayer();
        setNewWordOpponent();
    }

    public void charTyped(Player player) {
        Platform.runLater(() -> {
            if(this.player.getUser().getId() == player.getUser().getId()) {
//                this.player = player;
                Label_Word_Typed.setText(player.getTypedChars());
            }else if(opponent.getUser().getId() == player.getUser().getId()) {
//                this.opponent = player;
                Label_Opponent_Word_Typed.setText(player.getTypedChars());
            }
        });

    }

    private void setNewWordPlayer() {
        Label_Word.setText(player.getCurrentWord());
        Label_Word_Typed.setText("");
    }

    private void setNewWordOpponent() {
        Label_Opponent_Word.setText(opponent.getCurrentWord());
        Label_Opponent_Word_Typed.setText("");
    }


}
