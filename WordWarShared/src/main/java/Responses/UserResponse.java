package Responses;

import Models.User;
import lombok.Getter;
import lombok.Setter;

public class UserResponse implements IRestResponse<User> {
    @Getter @Setter private String status;
    @Getter @Setter private User value;
}
