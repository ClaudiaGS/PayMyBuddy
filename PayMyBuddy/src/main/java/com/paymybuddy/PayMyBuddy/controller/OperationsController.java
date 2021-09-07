package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.service.OperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationsController {
    @Autowired
    OperationsService operationsService;
    
    @GetMapping("/authentification")
    public boolean authentificate(@RequestParam String email,@RequestParam String password) {
        return operationsService.authentificate(email,password);
    }
}
