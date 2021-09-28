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
    BankAccountService bankAccountService;
    
    private static final Logger logger = LogManager.getLogger("TransactionService");
    
    @Override
    public Transaction createTransaction(String transactionDescription, double transactionReceivedAmount, int userIDSender, int userIDReceiver) {
        HashMap<String,String>paramsSender = new HashMap<>();
        HashMap<String,String>paramsReceiver=new HashMap<>();
        logger.info("sender updated amount "+bankAccountService.updateAmount(userIDSender,transactionReceivedAmount,"substract"));
        paramsSender.put("bankAccountAmount",String.valueOf(bankAccountService.updateAmount(userIDSender,transactionReceivedAmount,"substract")));
        paramsReceiver.put("bankAccountAmount",String.valueOf(bankAccountService.updateAmount(userIDReceiver,transactionReceivedAmount,"add")));
    
        Connection connection = null;
        boolean result = true;
        try {
        
            connection = this.dataSource.getConnection();
            connection.setAutoCommit(false);
    
            result = result && bankAccountService.updateBankAccount(connection, bankAccountService.readUsersBankAccount(userIDSender).getBankAccountID(),paramsSender);
            result = result && bankAccountService.updateBankAccount(connection, bankAccountService.readUsersBankAccount(userIDReceiver).getBankAccountID(),paramsReceiver);
    
            result = result && transactionRepository.createTransaction(connection, transactionDescription,transactionReceivedAmount,userIDSender,userIDReceiver);
    
    
        } catch (SQLException e) {
            logger.error(e);
            result = false;
        } finally {
        
            if(connection != null) {
                try {
                    if ( !result ) {
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
        return null;
    }
    
    @Override
    public List<Transaction> readTransactionList() {
        return transactionRepository.readTransactionList();
    }
    
    @Override
    public List<Transaction> readUsersTransactionList(int userIDSender) {
        return transactionRepository.readUsersTransactionList(userIDSender);
    }
    
    @Override
    public Transaction readTransaction(int transactionID) {
        return transactionRepository.readTransaction(transactionID);
    }
    
    @Override
    public boolean deleteTransaction(int transactionID) {
        return transactionRepository.deleteTransaction(transactionID);
    }
}
