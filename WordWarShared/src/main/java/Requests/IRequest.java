package Requests;

import Actions.ClientToServer;

public interface IRequest<T> {
    ClientToServer getAction();
    void setAction(ClientToServer action);
}
