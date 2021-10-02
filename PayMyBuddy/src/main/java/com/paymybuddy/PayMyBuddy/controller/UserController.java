package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    
    private static final Logger logger = LogManager.getLogger("UserService");
    
    @Autowired
    IUserService userService;
    
    @PostMapping("/createUser")
    public boolean createUser(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }
    
    @GetMapping("/readUserInfo")
    public User readUser(@RequestParam("userID") int userID) {
        return userService.readUser(userID);
    }
    
    @GetMapping("/readUserListInfo")
    public List<User> readUserList() {
        return userService.readUserList();
    }
    
    @PutMapping("/updateUser")
    public boolean updateUser(@RequestParam int userID, @RequestBody Map<String, Object> params) {
        return userService.updateUser(userID, params);
    }
    
    @PostMapping("/register")
    public int registration(String email, String password,
                            String rePassword, String firstName, String lastName, Date birthdate) {
        
        int result = 0;
        if (rePassword.equals(password)) {
            
            Account account = new Account();
            account.setAccountEmail(email);
            account.setAccountPassword(rePassword);
            
            User user = new User();
            user.setUserFirstName(firstName);
            user.setUserLastName(lastName);
            user.setUserBirthdate(birthdate);
            
            if (userService.registration(account, user)) {
                result = user.getUserID();
            }
            
        } else {
            logger.error("Password not the same as above");
        }
        
        return result;
    }
}



