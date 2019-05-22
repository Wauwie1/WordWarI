package Responses;

public class Response implements IResponse<Object> {
    private String status;
    private Object value;

    public Response() {
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object object) {
        this.value = object;
    }

    @Override
    public String toString() {

        return String.format("Status = %s. Value: %s.", status, value);
    }
}
