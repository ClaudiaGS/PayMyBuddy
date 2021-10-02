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
    public boolean createAccount(Connection connection,Account account);
    
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
    public Account readAccount(int accountID);
    
    
    /**
     *
     * @param accountID
     * @param params
     * @return boolean
     */
    public boolean updateAccount(int accountID, HashMap<String, Object> params);
    
    /**
     *
     * @param account
     * @return Account
     */
    public  Account authenticate(Account account);
    
    /**
     *
     * @param email
     * @return boolean
     */
    public boolean alreadyExist(String email);
}
