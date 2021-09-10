package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AccountService implements IAccountService{
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    UserService userService;
    
    private static final Logger logger = LogManager.getLogger("AccountService");
    
    @Override
    public Account createAccount(int userID, String email, String password) {
        Account account = null;
        if(userService.createUser(userService.readUser(userID))) {
            account = accountRepository.createAccount(userID,email,password);
        }
        return account;
    }
    
    @Override
    public Account readAccount(int accountID) {
        return accountRepository.readAccount(accountID);
    }
    
    @Override
    public List<Account> readAccountList() {
        return accountRepository.readAccountList();
    }
    
    @Override
    public boolean updateAccount(int accountID, HashMap<String, Object> params) {
        return accountRepository.updateAccount(accountID,params);
    }
    @Override
    public  int authentificate(String email, String password){
        return accountRepository.authentificate(email,password);
    }
//    @Autowired
////    private PasswordEncoder passwordEncoder;
//   @Autowired
//    UserRepository userRepository;

//    @Override
//    public Account registerNewAccount(Account newAccount){
//        List<Account>accountList=accountRepository.readAccountList();
//        if (accountList.contains(newAccount.getAccountEmail())) {
//            logger.error("There is an account with that email adress:" + newAccount.getAccountEmail());
//        }
//        System.out.println("here");
//        User user=new User();
//        user.setUserFirstName("firstnqme5");
//        user.setUserLastName("lastName5");
//        user.setUserBirthdate(null);
//        user.setUserProfilePicture(null);
//        user.setTransactionList(null);
//        user.setConnectionList(null);
//        user.setUsersBankAccount(null);
//        userRepository.createUser(user);
//
//        Account account = new Account();
//        account.setAccountEmail(newAccount.getAccountEmail());
//        account.setAccountPassword(passwordEncoder.encode(newAccount.getAccountPassword()));
//        account.setUserID(user.getUserID());
//        return accountRepository.createAccount(account);
//    }
}
