package Client.GUIControllers;

import Client.Interfaces.IGUIController;
import Client.Logic.ClientLogic;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegisterController implements IGUIController {

    @FXML public TextField Textfield_Username;
    @FXML public TextField Textfield_Password;
    @FXML public TextField Textfield_Password_Confirm;

    private ClientLogic logic;

    public void Button_Register_Clicked() {
        System.out.println(Textfield_Username.getText() + Textfield_Password.getText() + Textfield_Password_Confirm.getText());
    }

    @Override
    public void setLogic(ClientLogic logic) {
        this.logic = logic;
    }

    public void Image_Back_Clicked() {
        logic.goToLogin();
    }
}
