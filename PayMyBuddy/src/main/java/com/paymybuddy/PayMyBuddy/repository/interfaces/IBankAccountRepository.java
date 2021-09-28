package com.paymybuddy.PayMyBuddy.repository.interfaces;

import com.paymybuddy.PayMyBuddy.model.BankAccount;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public interface IBankAccountRepository {
    public BankAccount createBankAccount(double bankAccountAmount,String bankAccountCurrency, int userID);
    public BankAccount readBankAccount(int bankAccountID);
    public List<BankAccount> readBankAccountList();
    public BankAccount readUsersBankAccount(int userID);
    public double updateAmount(int userID,double transferedAmount, String operation );
    public boolean updateBankAccount(Connection connection,int BankAccountID, HashMap<String, String> params);
}
