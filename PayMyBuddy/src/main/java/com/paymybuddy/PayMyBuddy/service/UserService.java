package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.UserRepository;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    BankAccountService bankAccountService;
    
    
    private static final Logger logger = LogManager.getLogger("UserService");
    
    @Override
    public boolean createUser(User newUser) {
        logger.info("Created user with data:" + newUser);
        return userRepository.createUser(newUser);
    }
    
    @Override
    public List<User> readUserList() {
        List<User> userList = userRepository.readUserList();
        logger.info("User list is " + userList);
        return userList;
    }
    
    @Override
    public User readUser(int userID) {
        User user = userRepository.readUser(userID);
        logger.info("User with id " + userID + " is: " + user);
        return user;
    }
    
    @Override
    public boolean updateUser(int userID, HashMap<String, Object> params) {
        return userRepository.updateUser(userID, params);
    }
    
    @Override
    public int registration(String email, String password,
                            String rePassword, String firstName, String lastName, Date birthdate, int bankAccountNumber) {
        User user = new User();
        user.setUserFirstName(firstName);
        user.setUserLastName(lastName);
        user.setUserBirthdate(birthdate);
        
        List<User> userList = readUserList();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountNumber(11011);
        bankAccount.setBankAccountAmount(5000);
        bankAccount.setBankAccountCurrency("euro");
        if (password.equals(rePassword)) {
            if (!userList.contains(user)) {
                createUser(user);
                accountService.createAccount(user.getUserID(), email, password);
                bankAccountService.createBankAccount(bankAccount, user.getUserID());
            } else {
                logger.error("User already exists");
            }
        } else {
            logger.error("Password not the same as above");
        }
        return user.getUserID();
    }
}

