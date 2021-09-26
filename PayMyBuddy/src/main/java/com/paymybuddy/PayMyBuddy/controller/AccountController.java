package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;
    
    @PostMapping("/createAccount")
    public Account createAccount(@RequestParam int userID, @RequestParam String email, @RequestParam String password) {
        return accountService.createAccount(userID, email, password);
    }
    
    @GetMapping("/readAccountInfo")
    public Account readAccount(@RequestParam int accountID) {
        return accountService.readAccount(accountID);
    }
    
    @GetMapping("/readAccountListInfo")
    public List<Account> readAccountList() {
        return accountService.readAccountList();
    }
    
    @PutMapping("/updateAccount")
    public boolean updateAccount(@RequestParam int accountID, @RequestBody HashMap<String, Object> params) {
        return accountService.updateAccount(accountID, params);
    }
    
    @GetMapping("/authentificate")
    public Account authentificate(@RequestParam String email, @RequestParam String password) {
        return accountService.authenticate(email, password);
    }
    
//    @GetMapping("/home")
//    public String home(){
//        return"home";
//}
    

//    @GetMapping("/home")
//    public String LoginPage(Model model) {
//        Iterable<Account> listAccounts = accountService.readAccountList();
//
//        model.addAttribute("accounts", listAccounts);
//
//        return "LoginPage";
//    }
        
    }
