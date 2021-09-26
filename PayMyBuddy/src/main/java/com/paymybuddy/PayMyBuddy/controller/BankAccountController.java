package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;
    
    @PostMapping("/createBankAccount")
    public BankAccount createBankAccount(@RequestBody BankAccount bankAccount, @RequestParam int userID){
        return bankAccountService.createBankAccount(bankAccount, userID);
    }
    @GetMapping("/readBankAccount")
    public BankAccount readBankAccount(@RequestParam int bankAccountID) {
        return bankAccountService.readBankAccount(bankAccountID) ;
    }
    @GetMapping("/readBankAccountList")
    public List<BankAccount> readBankAccountList() {
        return bankAccountService.readBankAccountList() ;
    }
    @GetMapping("/readUsersBankAccount")
        public BankAccount readUsersBankAccount(int userID){
        return bankAccountService.readUsersBankAccount(userID);
    }
    @PutMapping("/updateBankAccount")
    public boolean updateBankAccount(@RequestParam int bankAccountID, @RequestBody HashMap<String, String> params) {
        return bankAccountService.updateBankAccount(bankAccountID,params);
    }
    
}
