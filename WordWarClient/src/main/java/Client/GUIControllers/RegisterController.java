package Client.GUIControllers;

import Client.Interfaces.IGUIController;
import Client.Logic.ClientLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class RegisterController implements IGUIController {

    @FXML public TextField Textfield_Username;
    @FXML public TextField Textfield_Password;
    @FXML public TextField Textfield_Password_Confirm;
    @FXML public Label Label_Error;

    private ClientLogic logic;

    public void Button_Register_Clicked() {
        String username = Textfield_Username.getText();
        String password = Textfield_Password.getText();

        if(password.compareTo(Textfield_Password_Confirm.getText()) == 0) {
            boolean success = logic.register(username, password);
            if(success) {
                Label_Error.setText("User successfully created!");
                Label_Error.setTextFill(Color.valueOf("Green"));
            }else {
                Label_Error.setText("Username already taken.");
                Label_Error.setTextFill(Color.valueOf("Red"));
            }
        }else {
            Label_Error.setText("Passwords do not match");
        }
    }

    @Override
    public void setLogic(ClientLogic logic) {
        this.logic = logic;
    }

    public void Image_Back_Clicked() {
        logic.goToLogin();
    }
}
