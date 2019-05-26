package Requests;

import Actions.ClientToServer;
import Models.User;

public class Request implements IRequest {
    private ClientToServer action;
    private User data;

    public ClientToServer getAction() {
        return action;
    }

    public void setAction(ClientToServer action) {
        this.action = action;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
