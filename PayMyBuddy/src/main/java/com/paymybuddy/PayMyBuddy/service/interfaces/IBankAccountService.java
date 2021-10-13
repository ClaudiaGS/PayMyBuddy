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
    public boolean createBankAccount(Connection connection,final BankAccount bankAccount);
    
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
    public BankAccount readBankAccount(final int bankAccountID);
    
    /**
     *
     * @param userID
     * @return BankAccount
     */
    public BankAccount readUsersBankAccount(final int userID);
    
    /**
     *
     * @param userID
     * @param transferedAmount
     * @param operation
     * @return double
     */
    public double updateAmount(final int userID,final double transferedAmount, final String operation );
    
    /**
     *
     * @param connection
     * @param bankAccountID
     * @param params
     * @return boolean
     */
    public boolean updateBankAccount(Connection connection,final int bankAccountID,final HashMap<String,Object> params);
    
    /**
     *
     * @param amount
     * @param amountForOperation
     * @param operation
     * @return double
     */
    public double updateAmountPersonalAccount(double amount,final double amountForOperation,final String operation);
}


