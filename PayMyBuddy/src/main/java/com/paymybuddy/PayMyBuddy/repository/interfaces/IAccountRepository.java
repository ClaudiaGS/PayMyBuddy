package com.paymybuddy.PayMyBuddy.repository.interfaces;

import com.paymybuddy.PayMyBuddy.model.Account;

import java.sql.Connection;
import java.util.List;

public interface IAccountRepository {
    
    /**
     *
     * @param connection
     * @param account
     * @return boolean
     */
    public boolean createAccount(Connection connection,final Account account);
  
    /**
     *
     * @return List<Account>
     */
    public List<Account> readAccountList();
    
    /**
     *
     * @param accountID
     * @return Account
     */
    public Account readAccount(final int accountID);
    
    /**
     *
     * @param userID
     * @return Account
     */
    public Account readUsersAccount(final int userID);
    
    /**
     *
     * @param account
     * @return boolean
     */
    public boolean updateAccount(final Account account);
    
    /**
     *
     * @param account
     * @return boolean
     */
    public Account authenticate(final Account account);
    
    /**
     *
     * @param email
     * @return boolean
     */
    public boolean alreadyExist(final String email);
}


