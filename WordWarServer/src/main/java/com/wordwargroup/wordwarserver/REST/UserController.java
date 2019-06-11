package com.wordwargroup.wordwarserver.REST;

import Models.User;
import Responses.IRestResponse;
import Responses.UserResponse;
import com.wordwargroup.wordwarserver.REST.Repositories.IDatabase;
import com.wordwargroup.wordwarserver.REST.Repositories.MySQLRepository;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    private static final IDatabase database = new MySQLRepository();

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public IRestResponse<User> login(@RequestParam(value="username", defaultValue="") String username,
                                     @RequestParam(value="password", defaultValue="") String password) {

        IRestResponse response = new UserResponse();
        User user = database.login(username, password);

        if(user != null){
            response.setStatus("200");
            response.setValue(user);
        }else {
            response.setStatus("401");
            response.setValue(null);
        }

        return response;
    }
}
