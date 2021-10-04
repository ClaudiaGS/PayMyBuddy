package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.config.DataBase;
import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.interfaces.IUserRepository;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService {
    private static final Logger logger = LogManager.getLogger("UserService");
    
    @Autowired
    IUserRepository userRepository;
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    BankAccountService bankAccountService;
    
    @Autowired
    public DataBase dataBase;
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IUserService#createUser(User)
     */
    @Override
    public boolean createUser(final User newUser) {
        if (newUser.getUserFirstName().isEmpty()|| newUser.getUserLastName().isEmpty()||newUser.getUserBirthdate()==null) {
            return false;
        } else {
            logger.info("Created user with data:" + newUser);
            return userRepository.createUser(newUser);
        }
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IUserService#readUserList()
     */
    @Override
    public List<User> readUserList() {
        List<User> userList = userRepository.readUserList();
        logger.info("User list is " + userList);
        return userList;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IUserService#readUser(int)
     */
    @Override
    public User readUser(final int userID) {
        User user = userRepository.readUser(userID);
        logger.info("User with id " + userID + " is: " + user);
        return user;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IUserService#updateUser(int, Map)
     */
    @Override
    public boolean updateUser(final int userID, final Map<String, Object> params) {
        
        User user = this.userRepository.readUser(userID);
        
        user.setUserFirstName(params.getOrDefault("firstName", (Object) user.getUserFirstName()).toString());
        user.setUserLastName(params.getOrDefault("lastName", (Object) user.getUserLastName()).toString());
        user.setUserBirthdate((Date) params.getOrDefault("firstName", (Object) user.getUserBirthdate()));
        
        return userRepository.updateUser(user);
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IUserService#registration(Account, User)
     */
    public boolean registration(Account account, User user) {
        
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountAmount(0);
        bankAccount.setBankAccountCurrency("euro");
        
        boolean success = true;
        Connection connection = null;
        
        try {
            connection = this.dataBase.getConnection();
            connection.setAutoCommit(false);
            
            if (!this.accountService.alreadyExist(account.getAccountEmail())) {
                
                success = this.createUser(user);
                logger.info("HERE ACCOUNT" + success);
                if (success) {
                    account.setUserID(user.getUserID());
                    bankAccount.setUserID(user.getUserID());
                }
                
                success = success && this.accountService.createAccount(connection, account);
                logger.info("here1" + success);
                success = success && this.bankAccountService.createBankAccount(connection, bankAccount);
                logger.info("here2" + success);
            } else {
                success = false;
                logger.error("User already exists");
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            success = false;
        } finally {
            if (connection != null) {
                try {
                    if (!success) {
                        connection.rollback();
                    } else {
                        connection.commit();
                    }
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    logger.error(e);
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        logger.info("Registration is " + success);
        return success;
    }
}



