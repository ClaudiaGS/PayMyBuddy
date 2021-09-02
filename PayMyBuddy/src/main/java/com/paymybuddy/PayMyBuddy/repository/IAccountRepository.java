package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.model.Account;

import java.util.HashMap;
import java.util.List;

public interface IAccountRepository {

    public Account createAccount(String email, String password);
    public Account readAccount(int accountID);
    public List<Account> readAccountList();
    public Account updateAccount(int accountID, HashMap<String, String> params);
    
}

