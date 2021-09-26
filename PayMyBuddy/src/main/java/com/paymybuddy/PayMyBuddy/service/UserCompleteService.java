package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.*;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserCompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCompleteService implements IUserCompleteService {
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;
    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    ContactService contactService;
    @Autowired
    TransactionService transactionService;
    
    @Override
    public UserComplete login(String email, String password) {
        UserComplete userComplete = new UserComplete();
        
        Account account = accountService.authenticate(email, password);
        if (account != null) {
            User user = userService.readUser(account.getUserID());
            
            BankAccount bankAccount = new BankAccount();
            List<BankAccount> bankAccountList = bankAccountService.readBankAccountList();
            for (BankAccount bA : bankAccountList) {
                if (bA.getUserID() == account.getUserID()) {
                    bankAccount = bA;
                }
            }
            
            List<Contact> contactsList = contactService.readUsersContactList(account.getUserID());
            
            List<Transaction> transactionList = transactionService.readUsersTransactionList(account.getUserID());
            
            
            //create master user object with all data
            userComplete.setUserID(account.getUserID());
            userComplete.setUserFirstName(user.getUserFirstName());
            userComplete.setUserLastName(user.getUserLastName());
            userComplete.setUserBirthdate(user.getUserBirthdate());
            userComplete.setUsersBankAccount(bankAccount);
            userComplete.setContactList(contactsList);
            userComplete.setTransactionList(transactionList);
            userComplete.setAccount(account);
            return userComplete;
        } else {
            return null;
        }
    }
    
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
            userComplete.setUserBirthdate(userService.readUser(account.getUserID()).getUserBirthdate());
            for (BankAccount bA : bankAccountList) {
                if (bA.getUserID() == account.getUserID()) {
                    bankAccount = bA;
                    userComplete.setUsersBankAccount(bankAccount);
                    
                }
            }
            userComplete.setContactList(contactService.readUsersContactList(account.getUserID()));
            userComplete.setTransactionList(transactionService.readUsersTransactionList(account.getUserID()));
            userComplete.setAccount(account);
            userCompleteList.add(userComplete);
        }
        return userCompleteList;
        
    }
    
}
