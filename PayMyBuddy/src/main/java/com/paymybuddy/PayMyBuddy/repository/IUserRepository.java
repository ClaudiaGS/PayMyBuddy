package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.model.User;

import java.util.HashMap;
import java.util.List;

public interface IUserRepository {
    public boolean createUser(User newUser);
    public List<User> readUserList();
    public User readUser(int userID);
    public User updateUser(int userID, HashMap<String, Object> params);
}
