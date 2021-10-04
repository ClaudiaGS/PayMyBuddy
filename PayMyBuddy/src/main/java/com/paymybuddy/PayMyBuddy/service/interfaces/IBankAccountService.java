package com.paymybuddy.PayMyBuddy.service.interfaces;

import com.paymybuddy.PayMyBuddy.model.BankAccount;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public interface IBankAccountService {
    
    /**
     *
     * @param bankAccount
     * @return boolean
     */
    public boolean createBankAccount(Connection connection,BankAccount bankAccount);
    
    /**
     *
     * @return List<BankAccount>
     */
    public List<BankAccount> readBankAccountList();
    
    /**
     *
     * @param bankAccountID
     * @return BankAccount
     */
    public BankAccount readBankAccount(int bankAccountID);
    
    /**
     *
     * @param userID
     * @return BankAccount
     */
    public BankAccount readUsersBankAccount(int userID);
    
    /**
     *
     * @param userID
     * @param transferedAmount
     * @param operation
     * @return double
     */
    public double updateAmount(int userID,double transferedAmount, String operation );
    
    /**
     *
     * @param connection
     * @param bankAccountID
     * @param params
     * @return boolean
     */
    public boolean updateBankAccount(Connection connection,int bankAccountID, HashMap<String,Object> params);
    
    /**
     *
     * @param amount
     * @param amountForOperation
     * @param operation
     * @return double
     */
    public double updateAmountPersonalAccount(double amount,double amountForOperation, String operation);
}


