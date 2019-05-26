package Responses;

import Actions.ServerToClient;

public class Response implements IResponse {
    private ServerToClient action;
    private Object data;

    public Response() {
    }
    public ServerToClient getAction() {
        return action;
    }

    public void setAction(ServerToClient action) {
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {

        return String.format("Action = %s. Data: %s.", action, data);
    }
}
