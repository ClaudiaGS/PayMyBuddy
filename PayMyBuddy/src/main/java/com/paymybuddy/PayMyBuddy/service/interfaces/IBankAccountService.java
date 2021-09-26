package com.paymybuddy.PayMyBuddy.service.interfaces;

import com.paymybuddy.PayMyBuddy.model.BankAccount;

import java.util.HashMap;
import java.util.List;

public interface IBankAccountService {
    public BankAccount createBankAccount(BankAccount bankAccount, int userID);
    public BankAccount readBankAccount(int bankAccountID);
    public BankAccount readUsersBankAccount(int userID);
    public double updateAmount(int userID, double transferedAmount, String operation );
    public List<BankAccount> readBankAccountList();
    public boolean updateBankAccount(int BankAccountID, HashMap<String,String> params);
}
