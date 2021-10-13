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
     * @param connection
     * @param bankAccount
     * @return boolean
     */
    public boolean updateBankAccount(Connection connection,final BankAccount bankAccount);
}
   
