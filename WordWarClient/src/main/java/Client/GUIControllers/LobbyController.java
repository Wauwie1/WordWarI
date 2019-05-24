package Client.GUIControllers;

import Models.User;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.util.Duration;

public class LobbyController {

    User user;

    @FXML public Button Button_Play;
    @FXML public Label Label_Welcome;

    public void initialize(){
        setAnimations();
    }

    private void setAnimations() {
        ScaleTransition animation = new ScaleTransition(Duration.millis(2500), Button_Play);
        animation.setByX(0.3f);
        animation.setByY(0.3f);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setAutoReverse(true);
        animation.play();
    }

    public void setUser(User user){
        this.user = user;
        Label_Welcome.setText("Welcome back " + user.getUsername() + ". Ready to play?");
        System.out.println(this.user);
    }
}
