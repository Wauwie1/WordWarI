package Requests;

import Actions.ClientToServer;

public interface IRequest {
    ClientToServer getAction();
    void setAction(ClientToServer action);
    Object getData();
    void setData(Object data);
}
