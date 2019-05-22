package com.wordwargroup.wordwarserver.REST;

import Models.User;
import Responses.IResponse;
import Responses.Response;
import Responses.UserResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;


@RestController
public class UserController {

    private static final String database = "https://wordwarone-8c3e.restdb.io/rest/";

    @CrossOrigin
    @RequestMapping("/login")
    public IResponse<User> login(@RequestParam(value="username", defaultValue="") String username,
                           @RequestParam(value="password", defaultValue="") String password) {

        System.out.println(username);

        IResponse response = new UserResponse();
        response.setStatus("200");
        User user = new User();
        user.setUsername(username);
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
//        GETUser = database + "user?q=";


//        System.out.println(GETUser);
//        ResponseEntity<Object> response2 = restTemplate.exchange(GETUser, HttpMethod.GET, entity, Object.class);
//        System.out.println(response2.getBody());


        
        return response;
    }
}
