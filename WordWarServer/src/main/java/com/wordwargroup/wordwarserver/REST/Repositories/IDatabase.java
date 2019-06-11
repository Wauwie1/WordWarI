package com.wordwargroup.wordwarserver.REST.Repositories;

import Models.User;

import java.util.List;

public interface IDatabase {
    User login(String username, String password);
    List<String> getWords();
}
