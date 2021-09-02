package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    
    @PostMapping("/createUser")
    public boolean createUser(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }
    @GetMapping("/readUserInfo")
    public User readUser(@RequestParam("userID")int userID) {
        return userService.readUser(userID);
    }
    
    @GetMapping("/readUserListInfo")
    public List<User> readUserList() {
        return userService.readUserList();
    }
    
    @PutMapping("/updateUser")
    public boolean updateUser(@RequestParam int userID, @RequestBody HashMap<String, Object> params){
        return userService.updateUser(userID,params);
    }
    
}

