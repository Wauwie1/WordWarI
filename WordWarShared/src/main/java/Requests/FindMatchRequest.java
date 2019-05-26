package Requests;

import Actions.ClientToServer;
import Models.User;

public class FindMatchRequest implements IRequest<User> {
    private ClientToServer action;
    private User user;


    public ClientToServer getAction() {
        return action;
    }

    public void setAction(ClientToServer action) {
        this.action = action;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
