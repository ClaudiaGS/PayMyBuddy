package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.repository.TransactionRepository;
import com.paymybuddy.PayMyBuddy.service.interfaces.ITransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    BankAccountService bankAccountService;
    
    private static final Logger logger = LogManager.getLogger("TransactionService");
    @Override
    public Transaction createTransaction(String transactionDescription, double transactionReceivedAmount, int userIDSender, int userIDReceiver) {
        HashMap<String,String>paramsSender = new HashMap<>();
        HashMap<String,String>paramsReceiver=new HashMap<>();
        logger.info("sender updated amount "+bankAccountService.updateAmount(userIDSender,transactionReceivedAmount,"substract"));
        paramsSender.put("bankAccountAmount",String.valueOf(bankAccountService.updateAmount(userIDSender,transactionReceivedAmount,"substract")));
        paramsReceiver.put("bankAccountAmount",String.valueOf(bankAccountService.updateAmount(userIDReceiver,transactionReceivedAmount,"add")));
    
        bankAccountService.updateBankAccount(bankAccountService.readUsersBankAccount(userIDSender).getBankAccountID(),paramsSender);
        bankAccountService.updateBankAccount(bankAccountService.readUsersBankAccount(userIDReceiver).getBankAccountID(),paramsReceiver);
        
        return transactionRepository.createTransaction(transactionDescription,transactionReceivedAmount,userIDSender,userIDReceiver);
    
    }
    
    @Override
    public List<Transaction> readTransactionList() {
        return transactionRepository.readTransactionList();
    }
    
    @Override
    public List<Transaction> readUsersTransactionList(int userIDSender) {
        return transactionRepository.readUsersTransactionList(userIDSender);
    }
    
    @Override
    public Transaction readTransaction(int transactionID) {
        return transactionRepository.readTransaction(transactionID);
    }
    
    @Override
    public boolean deleteTransaction(int transactionID) {
        return transactionRepository.deleteTransaction(transactionID);
    }
}
