package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.model.UserComplete;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserCompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserCompleteController {
    @Autowired
    IUserCompleteService userCompleteService;
    
    @GetMapping("/loginUserComplete")
    public UserComplete login(@RequestBody Account account){
        return userCompleteService.login(account);
    }
    
    @GetMapping("/userCompleteList")
    public List<UserComplete> readUserCompleteList() {
        return userCompleteService.readUserCompleteList();
    }
    
    @GetMapping("/userComplete")
    public UserComplete readUserComplete(@RequestParam int userID){
        return userCompleteService.readUserComplete(userID);
    }
}
