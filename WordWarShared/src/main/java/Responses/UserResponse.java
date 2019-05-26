package Responses;

import Models.User;

public class UserResponse implements IRestResponse<User> {
    private String status;
    private User value;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getValue() {
        return value;
    }

    public void setValue(User user) {
        this.value = user;
    }
}
