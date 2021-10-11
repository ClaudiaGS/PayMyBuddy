package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.*;
import com.paymybuddy.PayMyBuddy.service.interfaces.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCompleteService implements IUserCompleteService {
    @Autowired
    IUserService userService;
    @Autowired
    IAccountService accountService;
    @Autowired
    IBankAccountService bankAccountService;
    @Autowired
    IContactService contactService;
    @Autowired
    ITransactionService transactionService;
    
    private static final Logger logger = LogManager.getLogger("UserCompleteService");
    
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IUserCompleteService#login(Account)
     */
    @Override
    public UserComplete login(Account account) {
        
        UserComplete userComplete = new UserComplete();
        account = accountService.authenticate(account);
        if (account != null) {
            
            User user = userService.readUser(account.getUserID());
            
            BankAccount bankAccount = bankAccountService.readUsersBankAccount(account.getUserID());
            List<Contact> contactsList = contactService.readUsersContactList(account.getUserID());
            
            List<Transaction> transactionList = transactionService.readUsersTransactionList(account.getUserID());
            
            //create master user object with all data
            userComplete.setUserID(account.getUserID());
            userComplete.setUserFirstName(user.getUserFirstName());
            userComplete.setUserLastName(user.getUserLastName());
            userComplete.setUsersBankAccount(bankAccount);
            userComplete.setContactList(contactsList);
            userComplete.setTransactionList(transactionList);
            userComplete.setAccount(account);
            return userComplete;
        } else {
            logger.error("Cannot authenticate");
            return null;
        }
    }
    
    /**
     * (non-javadoc)
     *
     * @see IUserCompleteService#readUserCompleteList()
     */
    @Override
    public List<UserComplete> readUserCompleteList() {
        List<UserComplete> userCompleteList = new ArrayList<UserComplete>();
        
        BankAccount bankAccount = new BankAccount();
        List<BankAccount> bankAccountList = bankAccountService.readBankAccountList();
        
        for (Account account : accountService.readAccountList()) {
            UserComplete userComplete = new UserComplete();
            userComplete.setUserID(account.getUserID());
            userComplete.setUserFirstName(userService.readUser(account.getUserID()).getUserFirstName());
            userComplete.setUserLastName(userService.readUser(account.getUserID()).getUserLastName());
            userComplete.setUsersBankAccount(bankAccountService.readUsersBankAccount(account.getUserID()));
            
            userComplete.setContactList(contactService.readUsersContactList(account.getUserID()));
            userComplete.setTransactionList(transactionService.readUsersTransactionList(account.getUserID()));
            userComplete.setAccount(account);
            userCompleteList.add(userComplete);
        }
        return userCompleteList;
    }
    
    /**
     * (non-javadoc)
     *
     * @see IUserCompleteService#readUserComplete(int userID)
     */
    @Override
    public UserComplete readUserComplete(int userID) {
        UserComplete userComplete = new UserComplete();
        User user = userService.readUser(userID);
        Account account = accountService.readUsersAccount(userID);
        
        userComplete.setUserID(account.getUserID());
        userComplete.setUserFirstName(user.getUserFirstName());
        userComplete.setUserLastName(user.getUserLastName());
        userComplete.setUsersBankAccount(bankAccountService.readUsersBankAccount(userID));
        List<Contact> contactsList = contactService.readUsersContactList(userID);
    
        List<Transaction> transactionList = transactionService.readUsersTransactionList(userID);
        userComplete.setContactList(contactsList);
        userComplete.setTransactionList(transactionList);
        userComplete.setAccount(account);
        return userComplete;
    }
    
}
