package com.wordwargroup.wordwarserver.REST.Repositories;

import Models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockDatabaseRepository implements IDatabase {

    private Map<User, String> users;

    public MockDatabaseRepository() {
        users = new HashMap<>();
        User user = new User();
        user.setUsername("Test123");
        user.setId(5);

        users.put(user, "Wachtwoord123");
    }
    @Override
    public User login(String username, String password) {
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
    public String getWord(int id) {
        return null;
    }

    @Override
    public List<String> getWords() {
        return null;
    }
}
