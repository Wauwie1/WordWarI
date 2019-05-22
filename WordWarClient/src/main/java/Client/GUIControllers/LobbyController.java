package Client.GUIControllers;

import Models.User;

public class LobbyController {

    User user;

    public void setUser(User user){
        this.user = user;
        System.out.println(this.user);
    }
}
