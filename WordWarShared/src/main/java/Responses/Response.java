package Responses;

public class Response<T> implements IResponse<T> {
    private String status;
    private T value;

    public Response() {
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T object) {
        this.value = object;
    }

    @Override
    public String toString() {

        return String.format("Status = %s. Value: %s.", status, value);
    }
}
