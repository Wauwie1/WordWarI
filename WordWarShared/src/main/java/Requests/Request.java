package Requests;

import Actions.ClientToServer;

public class Request implements IRequest {
    private ClientToServer action;

    public ClientToServer getAction() {
        return action;
    }

    public void setAction(ClientToServer action) {
        this.action = action;
    }
}
