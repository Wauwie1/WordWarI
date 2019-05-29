package Requests;

import Actions.ClientToServer;
import lombok.Getter;
import lombok.Setter;

public class Request implements IRequest {

    @Getter @Setter private ClientToServer action;
    @Getter @Setter private Object data;
}
