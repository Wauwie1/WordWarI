package Client.GUIControllers;

import Responses.IResponse;
import Responses.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Scanner;

public class LoginController {

    @FXML
    public TextField Textfield_Username;
    @FXML
    public PasswordField Textfield_Password;


    public void Button_Login_Clicked(ActionEvent actionEvent) throws Exception {
        String username = Textfield_Username.getText();
        String password = Textfield_Password.getText();

        System.out.println(username);
        System.out.println(password);

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        switchToLobby(stage);


//
//        RestTemplate restTemplate = new RestTemplate();
//        IResponse<Object> greeting = restTemplate.getForObject("http://localhost:8081/login", Response.class);
//        System.out.println(greeting.toString());

//        String URL = "ws://localhost:8081/tutorialspoint-websocket";
//        WebSocketClient client = new StandardWebSocketClient();
//
//        WebSocketStompClient stompClient = new WebSocketStompClient(client);
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//
//        StompSessionHandler sessionHandler = new MyStompSessionHandler();
//        stompClient.connect(URL, sessionHandler);
//
//        new Scanner(System.in).nextLine();
    }

    private void switchToLobby(Stage stage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lobby.fxml"));
        Parent lobbyParent = loader.load();
        Scene lobbyScene = new Scene(lobbyParent, 900, 500);

        LobbyController controller = loader.getController();
        controller.setUser("test");


        stage.setScene(lobbyScene);
        stage.show();
    }
}
