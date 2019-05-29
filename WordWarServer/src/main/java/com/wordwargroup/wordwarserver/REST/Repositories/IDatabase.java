package com.wordwargroup.wordwarserver.REST.Repositories;

import Models.User;

public interface IDatabase {
    User login(String username, String password);
    String getWord(int id);
}
