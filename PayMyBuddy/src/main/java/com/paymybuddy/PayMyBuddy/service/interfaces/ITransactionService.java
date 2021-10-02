package com.paymybuddy.PayMyBuddy.service.interfaces;

import com.paymybuddy.PayMyBuddy.model.Transaction;

import java.util.List;

public interface ITransactionService {
    
    /**
     *
     * @param transaction
     * @return boolean
     */
    public boolean createTransaction(Transaction transaction);
    
    /**
     *
     * @return List<Transaction>
     */
    public List<Transaction> readTransactionList();
    
    
    /**
     *
     * @param transactionID
     * @return Transaction
     */
    public Transaction readTransaction(int transactionID);
    
    
    /**
     *
     * @param userID
     * @return List<Transaction>
     */
    public List<Transaction> readUsersTransactionList(int userID);
    
}