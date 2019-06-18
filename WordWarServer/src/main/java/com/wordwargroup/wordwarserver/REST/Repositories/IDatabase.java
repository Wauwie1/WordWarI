package com.wordwargroup.wordwarserver.REST.Repositories;

import Models.User;
import com.github.windpapi4j.WinAPICallFailedException;

import java.util.List;

public interface IDatabase {
    User login(String username, String password);
    List<String> getWords();

    boolean register(User user);
}
