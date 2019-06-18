package Client.Repositories;

import Client.Interfaces.ILoginRepository;
import Models.User;
import Responses.IResponse;
import Responses.IRestResponse;
import Responses.UserResponse;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class LoginRepository implements ILoginRepository {

    private final String server = "http://localhost:8081/";

    public User login(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        String url = server + "login?username=" + username + "&password=" + password;
        IRestResponse<User> response = restTemplate.getForObject(url, UserResponse.class);

        User user = null;
        try {
            user = response.getValue();
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }

        return user;
    }

    public boolean register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<User> request = new HttpEntity<>(user);
        boolean success = false;
        try {
            success = restTemplate.postForObject(server + "register", request, Boolean.class);
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return success;
        }
    }
}
