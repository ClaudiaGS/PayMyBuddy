package com.paymybuddy.PayMyBuddy.repository.interfaces;

import com.paymybuddy.PayMyBuddy.model.BankAccount;

import java.sql.Connection;
import java.util.List;

public interface IBankAccountRepository {
    
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
     * @param bankAccount
     * @return boolean
     */
    public boolean updateBankAccount(Connection connection,final BankAccount bankAccount);
}
   
