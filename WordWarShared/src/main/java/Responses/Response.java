package Responses;

import Actions.ServerToClient;
import lombok.Getter;
import lombok.Setter;

public class Response implements IResponse {
    @Getter @Setter private ServerToClient action;
    @Getter @Setter private Object data;

    public Response() {
    }

    @Override
    public String toString() {

        return String.format("Action = %s. Data: %s.", action, data);
    }
}
