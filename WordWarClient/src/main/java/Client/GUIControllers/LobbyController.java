package Client.GUIControllers;

import Client.MyStompSessionHandler;
import Models.User;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class LobbyController {

    private User user;

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

    public void Button_Play_Click(ActionEvent actionEvent) {
        String URL = "ws://localhost:8081/wordwarone";
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        ((MyStompSessionHandler) sessionHandler).setUser(user);
        stompClient.connect(URL, sessionHandler);
    }
}
