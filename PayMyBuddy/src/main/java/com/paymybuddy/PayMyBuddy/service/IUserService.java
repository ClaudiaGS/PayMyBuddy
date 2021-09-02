package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.User;

import java.util.HashMap;
import java.util.List;

public interface IUserService {
    public boolean createUser(User newUser);
    public List<User> readUserList();
    public User readUser(int userID);
    public boolean updateUser(int userID, HashMap<String, Object> params);
}
