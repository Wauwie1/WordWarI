package Client.GUIControllers;

import Client.Logic.ClientLogic;
import Client.Interfaces.IGUIController;
import Client.Interfaces.ILoginRepository;
import Client.Repositories.LoginRepository;
import Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;

public class LoginController implements IGUIController {

    private ILoginRepository loginRepository;
    @Getter private ClientLogic logic;

    @FXML public TextField Textfield_Username;
    @FXML public PasswordField Textfield_Password;
    @FXML public Label Label_Error;
    @FXML public AnchorPane AnchorPane_Main;

    public LoginController() {
        loginRepository = new LoginRepository();
        logic = new ClientLogic();
    }

    @Override
    public void initialize() {

    }

    public void Button_Login_Clicked(ActionEvent actionEvent) throws Exception {
        try {
            Stage stage = (Stage) AnchorPane_Main.getScene().getWindow();
            logic.getUiController().setStage(stage);
            login();
        }catch (Exception exception) {
            System.out.println("Could not connect to server.");
            Label_Error.setText("Could not connect to server");
        }
    }

    private void login() {
        String username = Textfield_Username.getText();
        String password = Textfield_Password.getText();

        User user = loginRepository.login(username, password);

        if(user != null) {
            logic.login(user);
        }else {
            Label_Error.setText("Incorrect password/username");
        }
    }


}
