package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BankAccountService implements IBankAccountService{
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    UserService userService;
    
    @Override
    public BankAccount createBankAccount(BankAccount bankAccount, int userID) {
        if(userService.createUser(userService.readUser(userID))) {
            bankAccount = bankAccountRepository.createBankAccount(bankAccount,userID);
        }
        return bankAccount;
    }
    
    @Override
    public BankAccount readBankAccount(int bankAccountID) {
        return bankAccountRepository.readBankAccount(bankAccountID) ;
    }
    
    @Override
    public List<BankAccount> readBankAccountList() {
        return bankAccountRepository.readBankAccountList();
    }
    
    @Override
    public boolean updateBankAccount(int bankAccountID, HashMap<String, Object> params) {
        return bankAccountRepository.updateBankAccount(bankAccountID,params);
    }
}
