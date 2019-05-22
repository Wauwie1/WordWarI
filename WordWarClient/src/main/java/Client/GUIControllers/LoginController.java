package Client.GUIControllers;

import Client.Interfaces.ILoginRepository;
import Client.Repositories.LoginRepository;
import Models.User;
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

    ILoginRepository loginRepository;

    @FXML
    public TextField Textfield_Username;
    @FXML
    public PasswordField Textfield_Password;

    public LoginController() {
        loginRepository = new LoginRepository();
    }

    public void Button_Login_Clicked(ActionEvent actionEvent) throws Exception {


        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        login(stage);






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

    private void login(Stage stage) throws IOException {
        String username = Textfield_Username.getText();
        String password = Textfield_Password.getText();

        User user = loginRepository.login(username, password);

        switchToLobby(stage, user);
    }

    private void switchToLobby(Stage stage, User user) throws IOException
    {
        // Create lobby scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lobby.fxml"));
        Parent lobbyParent = loader.load();
        Scene lobbyScene = new Scene(lobbyParent, 900, 500);

        // Pass user data to controller
        LobbyController controller = loader.getController();
        controller.setUser(user);

        // Display lobby scene
        stage.setScene(lobbyScene);
        stage.show();
    }
}
