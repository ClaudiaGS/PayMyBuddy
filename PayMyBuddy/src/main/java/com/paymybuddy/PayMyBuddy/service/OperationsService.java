package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.repository.OperationsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationsService implements IOperationsService {
    @Autowired
    OperationsRepository operationsRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    BankAccountService bankAccountService;
    
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
    @Override
    public double processSendMoney(String transactionDescription, double transactionDebitedAmount, int userIDSender, int userIDReceiver){
        Transaction transaction=transactionService.createTransaction(transactionDescription,transactionDebitedAmount,userIDSender,userIDReceiver);
        BankAccount bankAccount= new BankAccount();
        bankAccount.setUserID(userIDSender);
        List<BankAccount> bankAccountList=new ArrayList<BankAccount>();
        bankAccountList=bankAccountService.readBankAccountList();
        
        
        bankAccountService.updateBankAccount()
    }
    
    }
