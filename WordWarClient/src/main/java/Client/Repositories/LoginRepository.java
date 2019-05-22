package Client.Repositories;

import Client.Interfaces.ILoginRepository;
import Models.User;
import Responses.IResponse;
import Responses.Response;
import Responses.UserResponse;
import org.springframework.web.client.RestTemplate;

public class LoginRepository implements ILoginRepository {

    private final String server = "http://localhost:8081/";

    public User login(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        IResponse<User> response = restTemplate.getForObject(server + "login?username=" + username, UserResponse.class);

        User user = null;
        try {
            user = response.getValue();
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }

        return user;
    }

    public boolean register() {
        return false;
    }
}
