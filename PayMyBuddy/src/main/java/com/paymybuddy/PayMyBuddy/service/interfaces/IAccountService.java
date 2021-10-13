package com.paymybuddy.PayMyBuddy.service.interfaces;

import com.paymybuddy.PayMyBuddy.model.Account;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public interface IAccountService {
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
    public Account readAccount( final int accountID);
    
    /**
     *
     * @param userID
     * @return Account
     */
    public Account readUsersAccount(final int userID);
    
    /**
     *
     * @param accountID
     * @param params
     * @return boolean
     */
    public boolean updateAccount(final int accountID,final HashMap<String, Object> params);
    
    /**
     *
     * @param account
     * @return Account
     */
    public  Account authenticate(final Account account);
    
    /**
     *
     * @param email
     * @return boolean
     */
    public boolean alreadyExist(final String email);
}
