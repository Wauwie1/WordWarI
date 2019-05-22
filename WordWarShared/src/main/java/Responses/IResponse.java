package Responses;

public interface IResponse<T> {
    String getStatus();
    void setStatus(String status);
    T getValue();
    void setValue(T object);
    String toString();
}
