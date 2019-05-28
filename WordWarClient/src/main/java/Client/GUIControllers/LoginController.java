package Client.GUIControllers;

import Client.ClientGameController;
import Client.Interfaces.ILoginRepository;
import Client.Repositories.LoginRepository;
import Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    ILoginRepository loginRepository;
    private ClientGameController gameController;

    @FXML public TextField Textfield_Username;
    @FXML public PasswordField Textfield_Password;
    @FXML public Label Label_Error;

    public LoginController() {
        loginRepository = new LoginRepository();
        gameController = new ClientGameController();
    }

    public void Button_Login_Clicked(ActionEvent actionEvent) throws Exception {

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        gameController.setStage(stage);
        gameController.setLoginController(this);

        try {
            login(stage);
        }catch (Exception exception) {
            System.out.println("Could not connect to server.");
            Label_Error.setText("Could not connect to server");
        }
    }

    private void login(Stage stage) throws IOException {
        String username = Textfield_Username.getText();
        String password = Textfield_Password.getText();

        User user = loginRepository.login(username, password);

        if(user != null) {
            switchToLobby(stage, user);
        }else {
            Label_Error.setText("Incorrect password/username");
        }
    }

    private void switchToLobby(Stage stage, User user) throws IOException
    {
        // Create lobby scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lobby.fxml"));
        Parent lobbyParent = loader.load();
        Scene lobbyScene = new Scene(lobbyParent, 900, 500);

        // Pass user data to controller
        LobbyController controller = loader.getController();
        gameController.setLobbyController(controller);
        gameController.user = user;
        controller.setGameController(gameController);

        // Display lobby scene
        stage.setScene(lobbyScene);
        stage.show();
    }
}
