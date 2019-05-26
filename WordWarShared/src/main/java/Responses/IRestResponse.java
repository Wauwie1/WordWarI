package Responses;

public interface IRestResponse<T> {
    void setStatus(String status);
    void setValue(T object);
    T getValue();
}
