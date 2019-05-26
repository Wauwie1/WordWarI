package Requests;

import Actions.ClientToServer;
import Models.User;

public class FindMatchData {
    private ClientToServer action;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClientToServer getAction() {
        return action;
    }

    public void setAction(ClientToServer action) {
        this.action = action;
    }
}
