package com.wordwargroup.wordwarserver.REST;

import Models.User;
import Responses.IResponse;
import Responses.IRestResponse;
import Responses.UserResponse;
import com.wordwargroup.wordwarserver.REST.Repositories.IDatabase;
import com.wordwargroup.wordwarserver.REST.Repositories.MockDatabaseRepository;
import com.wordwargroup.wordwarserver.REST.Repositories.MySQLRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    private static final IDatabase database = new MySQLRepository();

    @CrossOrigin
    @RequestMapping("/login")
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
