package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;


@RestController
public class BankAccountController {
    @Autowired
    IBankAccountService bankAccountService;
    
    @PostMapping("/createBankAccount")
    public boolean createBankAccount(@RequestParam double bankAccountAmount,@RequestParam String bankAccountCurrency,@RequestParam int userID){
        BankAccount bankAccount=new BankAccount();
        bankAccount.setBankAccountAmount(bankAccountAmount);
        bankAccount.setBankAccountCurrency(bankAccountCurrency);
        bankAccount.setUserID(userID);
        Connection connection=null;
        return bankAccountService.createBankAccount(connection,bankAccount);
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
    public boolean updateBankAccount(@RequestParam int bankAccountID, @RequestBody HashMap<String,Object> params) {
        Connection connection=null;
        return bankAccountService.updateBankAccount(connection,bankAccountID,params);
    }
    
}
