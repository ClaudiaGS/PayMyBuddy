package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.UserComplete;
import com.paymybuddy.PayMyBuddy.service.UserCompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserCompleteController {
    @Autowired
    UserCompleteService userCompleteService;
    @GetMapping("/userCompleteList")
    public List<UserComplete> readUserCompleteList() {
        
        return userCompleteService.readUserCompleteList();
    }
}
