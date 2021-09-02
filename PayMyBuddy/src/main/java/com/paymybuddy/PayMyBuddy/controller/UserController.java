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
    
    @GetMapping("/userInfo")
    public User getUser(@RequestParam("userID")int userID) {
        System.out.println("user"+userID+":" + userService.getUser(userID));
        return userService.getUser(userID);
    }
    
    @GetMapping("/userListInfo")
    public List<User> getUserList() {
        System.out.println("userList in get userList "+ userService.getUserList());
        return userService.getUserList();
    }
    
  
    @PutMapping("/userModified")
    public User modifyUser(@RequestParam("userID") int userID, @RequestBody HashMap<String, Object> params){
        System.out.println(userService.modifyUser(userID,params));
        return userService.modifyUser(userID,params);
    }
    
}

