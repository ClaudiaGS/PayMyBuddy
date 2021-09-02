package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.model.BankAccount;

import java.util.HashMap;
import java.util.List;

public interface IBankAccountRepository {
    public boolean createBankAccount(BankAccount bankAccount);
    public BankAccount readBankAccount(int bankAccountID);
    public List<BankAccount> readBankAccountList();
    public BankAccount updateBankAccount(int BankAccountID, HashMap<String, Object> params);
}
