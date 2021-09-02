package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.BankAccount;

import java.util.HashMap;
import java.util.List;

public interface IBankAccountService {
    public BankAccount readBankAccount(int bankAccountID);
    public List<BankAccount> readBankAccountList();
    public BankAccount updateBankAccount(int bankAccountID, HashMap<String, Object> params);
}
