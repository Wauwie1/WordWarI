package Requests;

import Actions.ClientToServer;

public interface IRequest {
    ClientToServer getAction();
    void setAction(ClientToServer action);
}
