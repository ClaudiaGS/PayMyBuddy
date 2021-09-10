package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.config.DataBase;
import com.paymybuddy.PayMyBuddy.constants.DataBaseConstants;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository implements ITransactionRepository{
    private static final Logger logger = LogManager.getLogger("TransactionRepository");
    @Autowired
    DataBase dataBase;
    @Autowired
    Transaction transaction;
    @Override
    public Transaction createTransaction(String transactionDescription, double transactionDebitedAmount, int userIDSender, int userIDReceiver) {
        logger.info("Create transaction for user id: " + userIDSender);
        double feePercentage=0.5/100;
        transaction=null;
        transaction.setTransactionDescription(transactionDescription);
        transaction.setTransactionDebitedAmount(transactionDebitedAmount);
        transaction.setTransactionFeeAmount(transactionDebitedAmount*feePercentage);
        transaction.setTransactionReceivedAmount(transactionDebitedAmount-transaction.getTransactionFeeAmount());
        transaction.setUserIDSender(userIDSender);
        transaction.setUserIDReceiver(userIDReceiver);
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try (java.sql.Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.CREATE_TRANSACTION, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, transaction.getTransactionDescription());
            ps.setDouble(2, transaction.getTransactionDebitedAmount());
            ps.setDouble(3,transaction.getTransactionFeeAmount());
            ps.setDouble(4,transaction.getTransactionReceivedAmount());
            ps.setInt(5,transaction.getUserIDSender());
            ps.setInt(6,transaction.getUserIDReceiver());
            ps.execute();
            resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                transaction.setTransactionID(resultSet.getInt(1));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(resultSet);
            dataBase.closePreparedStatement(ps);
        }
        return transaction;
    }
    
    @Override
    public List<Transaction> readTransactionList() {
        logger.info("Reading transaction list from table");
        List<Transaction> transactionList = new ArrayList<Transaction>();
        transaction = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_TRANSACTION_LIST);
            rs = ps.executeQuery();
            while (rs.next()) {
                transaction= processRow(rs);
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
    
    protected Transaction processRow(ResultSet rs) throws SQLException {
        transaction=null;
        transaction.setTransactionID(rs.getInt(1));
        transaction.setTransactionDescription(rs.getString(2));
        transaction.setTransactionDebitedAmount(rs.getDouble(3));
        transaction.setTransactionFeeAmount(rs.getDouble(4));
        transaction.setTransactionReceivedAmount(rs.getDouble(5));
        transaction.setUserIDSender(rs.getInt(6));
        transaction.setUserIDReceiver(rs.getInt(7));
        return transaction;
    }
    
    
    @Override
    public List<Transaction> readUsersTransactionList(int userIDSender) {
        logger.info("Reading transaction list for user with id "+userIDSender);
        List<Transaction> usersTransactionList=new ArrayList<Transaction>();
        transaction = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_USERS_TRANSACTION_LIST);
            ps.setInt(1,userIDSender);
            rs = ps.executeQuery();
            while (rs.next()) {
                transaction= processRow(rs);
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
    
    @Override
    public Transaction readTransaction(int transactionID) {
        logger.info("Reading transaction with id: "+transactionID);
        transaction=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try(Connection con= dataBase.getConnection()){
            ps=con.prepareStatement(DataBaseConstants.READ_TRANSACTION);
            ps.setInt(1, transactionID);
            rs=ps.executeQuery();
            if(rs.next()){
                transaction=processRow(rs);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        return transaction;
    }
    
    @Override
    public boolean deleteTransaction(int transactionID) {
        logger.info("Deleting transaction with ID"+transactionID);
        PreparedStatement ps=null;
        ResultSet rs=null;
        boolean executed=false;
        transaction=readTransaction(transactionID);
        List<Transaction>transactionList=readTransactionList();
        transactionList.remove(transaction);
        try(Connection con= dataBase.getConnection()){
            ps=con.prepareStatement(DataBaseConstants.DELETE_TRANSACTION);
            ps.setInt(1,transactionID);
            rs=ps.executeQuery();
            executed=true;
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }return executed;
    }
}

