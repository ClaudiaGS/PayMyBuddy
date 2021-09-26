package com.paymybuddy.PayMyBuddy.repository.interfaces;

import com.paymybuddy.PayMyBuddy.model.BankAccount;

import java.util.HashMap;
import java.util.List;

public interface IBankAccountRepository {
    public BankAccount createBankAccount(BankAccount bankAccount, int userID);
    public BankAccount readBankAccount(int bankAccountID);
    public List<BankAccount> readBankAccountList();
    public BankAccount readUsersBankAccount(int userID);
    public double updateAmount(int userID,double transferedAmount, String operation );
    public boolean updateBankAccount(int BankAccountID, HashMap<String, String> params);
}
