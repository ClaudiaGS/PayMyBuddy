package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

@RestController
public class AccountController {
    @Autowired
    IAccountService accountService;
    
    @PostMapping("/createAccount")
    public boolean createAccount(@RequestParam int userID, @RequestParam String email, @RequestParam String password) {
        Connection connection = null;
        Account account = new Account();
        account.setAccountEmail(email);
        account.setAccountPassword(password);
        account.setUserID(userID);
        return accountService.createAccount(connection, account);
    }
    
    @GetMapping("/readAccountInfo")
    public Account readAccount(@RequestParam int accountID) {
        return accountService.readAccount(accountID);
    }
    
    @GetMapping("/readUsersAccountInfo")
    public Account readUsersAccount(@RequestParam int userID) {
        return accountService.readAccount(userID);
    }
    
    @GetMapping("/readAccountListInfo")
    public List<Account> readAccountList() {
        return accountService.readAccountList();
    }
    
    @PutMapping("/updateAccount")
    public boolean updateAccount(@RequestParam int accountID, @RequestBody HashMap<String, Object> params) {
        return accountService.updateAccount(accountID, params);
    }
    
    @GetMapping("/authenticate")
    public Account authenticate(@RequestParam String email, @RequestParam String password) {
        Account account = new Account();
        account.setAccountEmail(email);
        account.setAccountPassword(password);
        return accountService.authenticate(account);
    }
    
}
