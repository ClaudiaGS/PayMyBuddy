package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.config.DataBaseConfig;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService implements IUserService {
    private static final Logger logger = LogManager.getLogger("DataBaseConfig");
    @Autowired
    UserRepository userRepository;
    @Autowired
    User user;
    
    public DataBaseConfig dataBaseConfig = new DataBaseConfig();
    
    public User getUser(int userID) {
        return userRepository.readUser(userID);
    }
    public List<User> getUserList() {
        return userRepository.readUserList();
    }
    
    public User modifyUser(int userID, HashMap<String, Object> params) {
        return userRepository.updateUser(userID,params);
    }
}

