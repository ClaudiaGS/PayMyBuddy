package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.repository.AccountRepository;
import com.paymybuddy.PayMyBuddy.service.interfaces.IAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

@Service
public class AccountService implements IAccountService {
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    UserService userService;
    
    private static final Logger logger = LogManager.getLogger("AccountService");
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IAccountService#createAccount(Connection, Account)
     */
    @Override
    public boolean createAccount(Connection connection, Account account) {
        if (account.getAccountEmail().isEmpty()|| account.getAccountPassword().isEmpty()) {
            return false;
            
        } else {
            return accountRepository.createAccount(connection, account);
        }
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IAccountService#readAccountList()
     */
    @Override
    public List<Account> readAccountList() {
        List<Account> accountList = accountRepository.readAccountList();
        logger.info("Account list is " + accountList);
        return accountList;
    }
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IAccountService#readUsersAccount(int)
     */
    @Override
    public Account readUsersAccount(int userID) {
        return accountRepository.readUsersAccount(userID);
    }
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IAccountService#readAccount(int)
     */
    @Override
    public Account readAccount(int accountID) {
        Account account = accountRepository.readAccount(accountID);
        logger.info("Account with id " + accountID + " is: " + account);
        return account;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IAccountService#updateAccount(int, HashMap)
     */
    @Override
    public boolean updateAccount(int accountID, HashMap<String, Object> params) {
        
        Account account = this.accountRepository.readAccount(accountID);
        
        account.setAccountEmail((String) params.getOrDefault("accountEmail", account.getAccountEmail()));
        account.setAccountPassword((String) params.getOrDefault("accountPassword", account.getAccountPassword()));
        account.setUserID((Integer) params.getOrDefault("userID", account.getUserID()));
        
        return accountRepository.updateAccount(account);
        
        
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IAccountService#authenticate(Account)
     */
    @Override
    public Account authenticate(Account account) {
        logger.info("Authentified account " + accountRepository.authenticate(account));
        return accountRepository.authenticate(account);
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IAccountService#alreadyExist(String)
     */
    @Override
    public boolean alreadyExist(String email) {
        return accountRepository.alreadyExist(email);
        
    }
}
