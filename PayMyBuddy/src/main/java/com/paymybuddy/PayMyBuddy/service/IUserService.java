package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.User;

import java.util.HashMap;
import java.util.List;

public interface IUserService {
    public User getUser(int userID);
    public List<User> getUserList();
    public User modifyUser(int userID, HashMap<String, Object> params);
}
