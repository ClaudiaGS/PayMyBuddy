package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.model.Account;

import java.util.HashMap;
import java.util.List;

public interface IAccountRepository {

    public Account createAccount(int userID, String email, String password);
    public Account readAccount(int accountID);
    public Account readAccountEmailBased(String email);
    public List<Account> readAccountList();
    public boolean updateAccount(int accountID, HashMap<String, Object> params);
    
}

