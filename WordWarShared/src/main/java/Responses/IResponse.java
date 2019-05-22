package Responses;

public interface IResponse<T> {
    String getStatus();
    void setStatus(String status);
    T getValue();
    void setValue(Object object);
    String toString();
}
