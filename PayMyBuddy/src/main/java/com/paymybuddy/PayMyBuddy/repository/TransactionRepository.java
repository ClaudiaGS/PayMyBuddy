package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.config.DataBase;
import com.paymybuddy.PayMyBuddy.constants.DataBaseConstants;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.repository.interfaces.ITransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository implements ITransactionRepository {
    private static final Logger logger = LogManager.getLogger("TransactionRepository");
    @Autowired
    DataBase dataBase;
    @Autowired
    Transaction transaction;
    
    private Transaction processRow(ResultSet rs) throws SQLException {
        transaction = new Transaction();
        transaction.setTransactionID(rs.getInt(1));
        transaction.setTransactionDescription(rs.getString(2));
        transaction.setTransactionDebitedAmount(rs.getDouble(3));
        transaction.setTransactionFeeAmount(rs.getDouble(4));
        transaction.setTransactionReceivedAmount(rs.getDouble(5));
        transaction.setUserIDSender(rs.getInt(6));
        transaction.setUserIDReceiver(rs.getInt(7));
        return transaction;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.repository.interfaces.ITransactionRepository#createTransaction(Connection, Transaction)
     */
    @Override
    public boolean createTransaction(Connection connection, Transaction transaction) {
        
        double feePercentage = 0.5 / 100;
        double feeAmount = transaction.getTransactionReceivedAmount() * feePercentage;
        
        boolean result=false;
        
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = connection.prepareStatement(DataBaseConstants.CREATE_TRANSACTION, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, transaction.getTransactionDescription());
            ps.setDouble(2, (transaction.getTransactionReceivedAmount() + feeAmount));
            ps.setDouble(3, feeAmount);
            ps.setDouble(4, transaction.getTransactionReceivedAmount());
            ps.setInt(5, transaction.getUserIDSender());
            ps.setInt(6, transaction.getUserIDReceiver());
    
            logger.debug(ps.toString());
            
            ps.execute();
            
            resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                transaction.setTransactionID(resultSet.getInt(1));
                result=true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        
        return result;
    }
    
    /**
     * (non-javadoc)
     *
     * @see ITransactionRepository#readTransactionList()
     */
    @Override
    public List<Transaction> readTransactionList() {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        transaction = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_TRANSACTION_LIST);
            
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            while (rs.next()) {
                transaction = processRow(rs);
                transactionList.add(transaction);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        return transactionList;
        
    }
    
    /**
     * (non-javadoc)
     *
     * @see ITransactionRepository#readTransaction(int)
     */
    @Override
    public Transaction readTransaction(int transactionID) {
        transaction = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_TRANSACTION);
            ps.setInt(1, transactionID);
    
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            if (rs.next()) {
                transaction = processRow(rs);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        return transaction;
    }
    
    /**
     * (non-javadoc)
     *
     * @see ITransactionRepository#readUsersTransactionList(int)
     */
    @Override
    public List<Transaction> readUsersTransactionList(int userIDSender) {
        List<Transaction> usersTransactionList = new ArrayList<Transaction>();
        transaction = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_USERS_TRANSACTION_LIST);
            ps.setInt(1, userIDSender);
    
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            while (rs.next()) {
                transaction = processRow(rs);
                usersTransactionList.add(transaction);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        return usersTransactionList;
    }
}

