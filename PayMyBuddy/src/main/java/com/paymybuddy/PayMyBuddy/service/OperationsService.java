package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.repository.OperationsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationsService implements IOperationsService {
    @Autowired
    OperationsRepository operationsRepository;
    @Autowired
    AccountService accountService;
    
    private static final Logger logger = LogManager.getLogger("OperationService");
    
    @Override
    public void createAccount(Account account) {
    
    
    }
    
    @Override
    public void registerAccount(Account account) {
    
    }
    
    @Override
    public boolean authentificate(String email, String password) {
       
        boolean authentificationSucceeded=false;
        String actualPassword=accountService.readAccountEmailBased(email).getAccountPassword();
        if(actualPassword.equals(operationsRepository.getPasswordEncoded(password))){
            authentificationSucceeded=true;
        }
        else authentificationSucceeded=false;
        logger.info("authetification = "+authentificationSucceeded);
        return authentificationSucceeded;
    }
}
