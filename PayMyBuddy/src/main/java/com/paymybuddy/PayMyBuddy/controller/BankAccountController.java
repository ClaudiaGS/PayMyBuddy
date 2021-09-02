package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.service.BankAccountService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Data
@RestController
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;
    
    @GetMapping("/getBankAccount")
    public BankAccount readBankAccount(@RequestParam int bankAccountID) {
        return bankAccountService.readBankAccount(bankAccountID) ;
    }
    @GetMapping("/getBankAccountList")
    public List<BankAccount> readBankAccountList() {
        return bankAccountService.readBankAccountList() ;
    }
    @PutMapping("/putBankAccount")
    public BankAccount updateBankAccount(@RequestParam int bankAccountID, @RequestBody HashMap<String, Object> params) {
        return bankAccountService.updateBankAccount(bankAccountID,params);
    }
}
