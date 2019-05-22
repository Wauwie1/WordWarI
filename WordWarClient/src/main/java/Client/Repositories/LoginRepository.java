package Client.Repositories;

import Client.Interfaces.ILoginRepository;
import Models.User;
import Responses.IResponse;
import Responses.Response;
import org.springframework.web.client.RestTemplate;

public class LoginRepository implements ILoginRepository {

    public User login(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        IResponse<User> response = restTemplate.getForObject("http://localhost:8081/login", Response.class);

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
