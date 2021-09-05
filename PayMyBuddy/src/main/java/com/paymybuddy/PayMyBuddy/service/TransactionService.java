package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public Transaction createTransaction(Transaction newTransaction) {
        return transactionRepository.createTransaction(newTransaction);
    
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
