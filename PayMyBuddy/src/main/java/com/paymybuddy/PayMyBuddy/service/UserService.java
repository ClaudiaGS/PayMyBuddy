package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;
    
    @Override
    public boolean createUser(User newUser) {
        return userRepository.createUser(newUser);
    }
    
    @Override
    public List<User> readUserList() {
        return userRepository.readUserList();
    }
    
    @Override
    public User readUser(int userID) {
        return userRepository.readUser(userID);
    }
    
    public boolean updateUser(int userID, HashMap<String, Object> params) {
        return userRepository.updateUser(userID,params);
    }
}

