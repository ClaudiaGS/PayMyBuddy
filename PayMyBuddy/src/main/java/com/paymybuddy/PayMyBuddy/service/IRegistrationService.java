package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Account;

public interface IRegistrationService {
    public void createAccount(Account account);
    public void registerAccount(Account account);
}
