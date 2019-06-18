package com.wordwargroup.wordwarserver.REST.Repositories;

import Models.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockDatabaseRepository implements IDatabase {

    private Map<User, String> users;
    private List<String> words;

    public MockDatabaseRepository() {
        users = new HashMap<>();
        User user = new User();
        user.setUsername("Test123");
        user.setId(5);

        users.put(user, "Wachtwoord123");
    }
    @Override
    public User login(String username, String password) {
        if(username == null || password == null) {
            return null;
        }

        for(Map.Entry<User, String> entry : users.entrySet()) {
            User userKey = entry.getKey();
            if(userKey.getUsername().compareTo(username) == 0){
                String passwordValue = entry.getValue();
                if(passwordValue.compareTo(password) == 0){
                    User user = new User();
                    user.setUsername(username);
                    System.out.println("Login successful");
                    return user;
                }
            }
        }
        System.out.println("Login failed");
        return null;
    }

    @Override
    public List<String> getWords() {
        words = new ArrayList<>();
        words.add("Telephone");
        words.add("Television");
        words.add("Washbasin");
        words.add("Computer");
        words.add("Radio");
        words.add("Appliances");
        words.add("Stereo");
        words.add("Sound");
        words.add("Music");
        words.add("Screen");
        words.add("Lights");

        return words;
    }

    @Override
    public boolean register(User user) {
        return false;
    }
}
