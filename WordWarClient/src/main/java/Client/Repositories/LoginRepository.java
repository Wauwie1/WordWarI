package Client.Repositories;

import Client.Interfaces.ILoginRepository;
import Models.User;
import Responses.IResponse;
import Responses.IRestResponse;
import Responses.UserResponse;
import lombok.extern.log4j.Log4j;
import org.springframework.web.client.RestTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Log4j
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
            log.error(exception);
        }

        return user;
    }

    public boolean register() {
        throw new NotImplementedException();
    }
}
