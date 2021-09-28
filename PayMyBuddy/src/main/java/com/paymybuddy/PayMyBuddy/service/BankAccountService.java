package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.repository.BankAccountRepository;
import com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BankAccountService implements IBankAccountService {
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    UserService userService;
    private static final Logger logger = LogManager.getLogger("BankAccountService");
    @Override
    public BankAccount createBankAccount(double bankAccountAmount,String bankAccountCurrency, int userID) {
        BankAccount bankAccount=new BankAccount();
        if(userService.readUser(userID)!=null) {
            bankAccount = bankAccountRepository.createBankAccount(bankAccountAmount,bankAccountCurrency,userID);
        }
        logger.info("Created bankAccount "+bankAccount);
        return bankAccount;
    }
    
    @Override
    public BankAccount readBankAccount(int bankAccountID) {
        BankAccount bankAccount=bankAccountRepository.readBankAccount(bankAccountID) ;
        logger.info("Bank account with id "+bankAccountID+" is "+bankAccount);
        return bankAccount;
    }
    
    @Override
    public List<BankAccount> readBankAccountList() {
        List<BankAccount>bankAccountList=bankAccountRepository.readBankAccountList();
        logger.info("Bank account list is "+bankAccountList);
        return bankAccountList;
    }
    
    @Override
    public BankAccount readUsersBankAccount(int userID) {
        BankAccount bankAccount=bankAccountRepository.readUsersBankAccount(userID) ;
        logger.info("Bank account for user with id "+userID+" is "+bankAccount);
        return bankAccount;
    }
    
    @Override
    public double updateAmount(int userID, double transferedAmount, String operation) {
        double amount=bankAccountRepository.updateAmount(userID,transferedAmount,operation);
        return amount;
    }
    
    @Override
    public boolean updateBankAccount(int bankAccountID, HashMap<String, String> params) {
        return bankAccountRepository.updateBankAccount(bankAccountID,params);
    }
}
