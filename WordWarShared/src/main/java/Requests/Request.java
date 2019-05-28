package Requests;

import Actions.ClientToServer;
import Models.User;

public class Request implements IRequest {
    private ClientToServer action;
    private Object data;

    public ClientToServer getAction() {
        return action;
    }

    public void setAction(ClientToServer action) {
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
