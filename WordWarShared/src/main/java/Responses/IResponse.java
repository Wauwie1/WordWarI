package Responses;

import Actions.ServerToClient;

public interface IResponse {
    ServerToClient getAction();
    void setAction(ServerToClient action);
    Object getData();
    void setData(Object object);
    String toString();
}
