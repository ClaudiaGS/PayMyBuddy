package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.config.DataBase;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.repository.TransactionRepository;
import com.paymybuddy.PayMyBuddy.service.interfaces.ITransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Service
public class TransactionService implements ITransactionService {
    
    @Autowired
    public DataBase dataSource;
    
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserService userService;
    @Autowired
    BankAccountService bankAccountService;
    
    private static final Logger logger = LogManager.getLogger("TransactionService");
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.ITransactionService#createTransaction(Transaction)
     */
    @Override
    public boolean createTransaction(Transaction transaction) {
        boolean result = true;
        double feePercentage = 0.5 / 100;
        double feeAmount = transaction.getTransactionReceivedAmount() * feePercentage;
        if (transaction.getTransactionReceivedAmount() > 0) {
            if (bankAccountService.readUsersBankAccount(transaction.getUserIDSender()).getBankAccountAmount() >= (transaction.getTransactionReceivedAmount() +feeAmount)) {
                HashMap<String, Object> paramsSender = new HashMap<>();
                HashMap<String, Object> paramsReceiver = new HashMap<>();
                paramsSender.put("bankAccountAmount", bankAccountService.updateAmount(transaction.getUserIDSender(), transaction.getTransactionReceivedAmount(), "substract"));
                paramsReceiver.put("bankAccountAmount", bankAccountService.updateAmount(transaction.getUserIDReceiver(), transaction.getTransactionReceivedAmount(), "add"));
                
                Connection connection = null;
                
                try {
                    
                    connection = this.dataSource.getConnection();
                    connection.setAutoCommit(false);
                    
                    result = result && bankAccountService.updateBankAccount(connection, bankAccountService.readUsersBankAccount(transaction.getUserIDSender()).getBankAccountID(), paramsSender);
                    result = result && bankAccountService.updateBankAccount(connection, bankAccountService.readUsersBankAccount(transaction.getUserIDReceiver()).getBankAccountID(), paramsReceiver);
                    
                    result = result && transactionRepository.createTransaction(connection, transaction);
                    
                    
                } catch (SQLException | ClassNotFoundException e) {
                    logger.error(e);
                    result = false;
                } finally {
                    
                    if (connection != null) {
                        try {
                            if (!result) {
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
            } else {
                logger.error("Not enough money.Cannot send !");
                result=false;
            }
        } else {
            logger.error("Transaction amount not valid");
            result = false;
        }
        return result;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.ITransactionService#readTransactionList()
     */
    @Override
    public List<Transaction> readTransactionList() {
        return transactionRepository.readTransactionList();
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.ITransactionService#readTransaction(int)
     */
    @Override
    public Transaction readTransaction(int transactionID) {
        return transactionRepository.readTransaction(transactionID);
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.ITransactionService#readUsersTransactionList(int)
     */
    @Override
    public List<Transaction> readUsersTransactionList(int userIDSender) {
        return transactionRepository.readUsersTransactionList(userIDSender);
    }
    
}


