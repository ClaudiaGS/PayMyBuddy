package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Account;

public interface IOperationsService {
    public void createAccount(Account account);
    public void registerAccount(Account account);
    public boolean authentificate(String email, String password);
}