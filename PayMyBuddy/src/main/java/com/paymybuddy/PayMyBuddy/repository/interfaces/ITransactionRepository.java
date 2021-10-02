package com.paymybuddy.PayMyBuddy.repository.interfaces;

import com.paymybuddy.PayMyBuddy.model.Transaction;

import java.sql.Connection;
import java.util.List;

public interface ITransactionRepository {
    /**
     *
     * @param connection
     * @param transaction
     * @return boolean
     */
    public boolean createTransaction(Connection connection,Transaction transaction);
    
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
