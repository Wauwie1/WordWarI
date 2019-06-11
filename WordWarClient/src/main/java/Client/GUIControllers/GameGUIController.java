package Client.GUIControllers;

import Client.Logic.ClientLogic;
import Client.Logic.ClientLobby;
import Client.Interfaces.IGUIController;
import Models.Player;
import Models.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.Setter;

import java.util.List;

public class GameGUIController implements IGUIController {

    @FXML public Label Label_Word;
    @FXML public Label Label_Word_Typed;
    @FXML public Label Label_Opponent_Word;
    @FXML public Label Label_Opponent_Word_Typed;
    @FXML public Label Label_Name_Player;
    @FXML public Label Label_Name_Opponent;
    @FXML public Label Label_Lives_Player;
    @FXML public Label Label_Lives_Opponent;
    @FXML public AnchorPane Pane_End;
    @FXML public Button Button_Lobby;
    @FXML public Label Label_End;
    @FXML public ImageView Black_Fade;

    @Setter private ClientLogic logic;
    private Scene scene;

    private Player player;
    private Player opponent;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setKeyListener() {
        this.scene.setOnKeyPressed(e -> {
            logic.sendKeyPress(e.getText(), player);
        });
    }

    public void createGameField() {
        ClientLobby lobby = logic.getLobby();
        User user = logic.getUser();
        List<Player> players = lobby.getPlayers();

        for (Player gamePlayer: players) {
            if(gamePlayer.getUser().getId() == user.getId()) {
                this.player = gamePlayer;
            }else {
                this.opponent = gamePlayer;
            }
        }

        Platform.runLater(() -> {
            Label_Name_Player.setText(player.getUser().getUsername());
            Label_Name_Opponent.setText(opponent.getUser().getUsername());
            setNewWordPlayer();
            setNewWordOpponent();
            setLivesPlayer();
            setLivesOpponent();
        });
    }

    private void setLivesOpponent() {
        String lives = String.valueOf(player.getLives());
        Label_Lives_Player.setText(lives);
    }

    private void setLivesPlayer() {
        String lives = String.valueOf(opponent.getLives());
        Label_Lives_Opponent.setText(lives);
    }

    public void charTyped(Player player) {
        Platform.runLater(() -> {
            if(this.player.getUser().getId() == player.getUser().getId()) {
                Label_Word_Typed.setText(player.getTypedChars());
            }else if(opponent.getUser().getId() == player.getUser().getId()) {
                Label_Opponent_Word_Typed.setText(player.getTypedChars());
            }
        });

    }

    private void setNewWordPlayer() {
        Platform.runLater(() -> {
            Label_Word.setText(player.getCurrentWord());
            Label_Word_Typed.setText("");
        });
    }

    private void setNewWordOpponent() {
        Platform.runLater(() -> {
            Label_Opponent_Word.setText(opponent.getCurrentWord());
            Label_Opponent_Word_Typed.setText("");
        });
    }


    public void newWord(Player player, Player playerOpponent) {
        int playerId = player.getUser().getId();
        if(playerId == this.player.getUser().getId()) {
            this.player = player;
            this.opponent = playerOpponent;
            setNewWordPlayer();

        }else if(playerId == opponent.getUser().getId()) {
            this.opponent = player;
            this.player = playerOpponent;
            setNewWordOpponent();
        }
        updateLivesPlayer();
        updateLivesOpponent();
    }

    private void updateLivesOpponent() {
        Platform.runLater(() -> {
            String lives = String.valueOf(opponent.getLives());
            Label_Lives_Opponent.setText(lives);
        });
    }

    private void updateLivesPlayer() {
        Platform.runLater(() -> {
            String lives = String.valueOf(player.getLives());
            Label_Lives_Player.setText(lives);
        });
    }

    public void endGame(Player winner) {
        Platform.runLater(() -> {
            Black_Fade.setVisible(true);
            Pane_End.setVisible(true);
            if(player.getUser().getId() == winner.getUser().getId()) {
                Label_End.setText("YOU'VE WON!");
            }else if(opponent.getUser().getId() == winner.getUser().getId()) {
                Label_End.setText("You've lost...");
            }
        });
    }

    public void Button_Lobby_Click() {
            logic.goToLobby();
    }
}
