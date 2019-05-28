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
    private static final String databaseString = "https://wordwarone-8c3e.restdb.io/rest/";

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


        response.setValue(user);

//        RestTemplate restTemplate = new RestTemplate();
//        final HttpHeaders headers = new HttpHeaders();
//        headers.add("x-apikey", "c79ad9b749660bf90536278e95c11e858af54");
//        headers.add("cache-control", "no-cache");
//        headers.setAccept(Arrays.asList(MediaType.ALL));
//        final HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        String sort = "{\"username\":\"tgouninm\"}";
//        String GETUser = null;
//
//        GETUser = databaseString + "user?q=";


//        System.out.println(GETUser);
//        ResponseEntity<Object> response2 = restTemplate.exchange(GETUser, HttpMethod.GET, entity, Object.class);
//        System.out.println(response2.getBody());


        
        return response;
    }
}
