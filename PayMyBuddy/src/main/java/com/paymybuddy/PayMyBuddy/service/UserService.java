package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.UserRepository;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger("UserService");
    
    @Override
    public boolean createUser(User newUser) {
        logger.info("Created user with data:" + newUser);
        return userRepository.createUser(newUser);
    }
    
    @Override
    public List<User> readUserList() {
        List<User> userList = userRepository.readUserList();
        logger.info("User list is " + userList);
        return userList;
    }
    
    @Override
    public User readUser(int userID) {
        User user = userRepository.readUser(userID);
        logger.info("User with id " + userID + " is: " + user);
        return user;
    }
    
    public boolean updateUser(int userID, HashMap<String, Object> params) {
        return userRepository.updateUser(userID, params);
    }
}

