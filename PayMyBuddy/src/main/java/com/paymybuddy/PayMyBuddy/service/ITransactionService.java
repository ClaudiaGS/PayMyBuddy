package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Transaction;

import java.util.List;

public interface ITransactionService {
    public Transaction createTransaction(Transaction transaction);
    public List<Transaction> readTransactionList();
    public List<Transaction> readUsersTransactionList(int userIDSender);
    public Transaction readTransaction(int transactionID);
    //public boolean updateTransaction(int contactID, HashMap<String, Object> params);
    public boolean deleteTransaction(int transactionID);
}
