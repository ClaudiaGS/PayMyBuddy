package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.config.DataBase;
import com.paymybuddy.PayMyBuddy.constants.DataBaseConstants;
import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.repository.interfaces.IBankAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BankAccountRepository implements IBankAccountRepository {
    @Autowired
    public DataBase dataBase;
    @Autowired
    BankAccount bankAccount;
    
    private BankAccount processRow(ResultSet rs) throws SQLException {
        bankAccount=new BankAccount();
        bankAccount.setBankAccountID(rs.getInt(1));
        bankAccount.setBankAccountAmount(rs.getDouble(2));
        bankAccount.setBankAccountCurrency(rs.getString(3));
        bankAccount.setUserID(rs.getInt(4));
        return bankAccount;
    }
    
    private static final Logger logger = LogManager.getLogger("BankAccountRepository");
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.repository.interfaces.IBankAccountRepository#createBankAccount(Connection,BankAccount)
     */
    @Override
    public boolean createBankAccount(Connection connection,BankAccount bankAccount) {
        boolean result=false;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try  {
            ps = connection.prepareStatement(DataBaseConstants.CREATE_BANK_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, bankAccount.getBankAccountAmount());
            ps.setString(2, bankAccount.getBankAccountCurrency());
            ps.setInt(3, bankAccount.getUserID());
    
            logger.debug(ps.toString());
            
            ps.execute();
            resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                bankAccount.setBankAccountID(resultSet.getInt(1));
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
     * @see com.paymybuddy.PayMyBuddy.repository.interfaces.IBankAccountRepository#readBankAccount(int)
     */
    @Override
    public BankAccount readBankAccount(int bankAccountID) {
        logger.info("Read bank account info for bank account ID: " + bankAccountID);
        bankAccount = new BankAccount();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_BANK_ACCOUNT);
            ps.setInt(1, bankAccountID);
            
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            if (rs.next()) {
                bankAccount = processRow(rs);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
            return bankAccount;
        }
    }
    
    /**
     * (non-javadoc)
     *
     * @see IBankAccountRepository#readBankAccountList() 
     */
    @Override
    public List<BankAccount> readBankAccountList() {
        logger.info("Reading bank account list from table");
        List<BankAccount> bankAccountList = new ArrayList<BankAccount>();
        bankAccount = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_BANK_ACCOUNT_LIST);
            
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            while (rs.next()) {
                bankAccount = processRow(rs);
                bankAccountList.add(bankAccount);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        return bankAccountList;
        
    }
    
    /**
     * (non-javadoc)
     *
     * @see IBankAccountRepository#readUsersBankAccount(int) 
     */
    @Override
    public BankAccount readUsersBankAccount(int userID){
        logger.info("Read bank account info for user with ID: " + userID);
        bankAccount = new BankAccount();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_USERS_BANK_ACCOUNT);
            ps.setInt(1, userID);
            
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            if (rs.next()) {
                bankAccount = processRow(rs);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
            return bankAccount;
        }
    }
   
    /**
     * (non-javadoc)
     *
     * @see IBankAccountRepository#updateBankAccount(Connection, BankAccount) 
     */
    @Override
    public boolean updateBankAccount(Connection connection,final BankAccount bankAccount) {
        boolean executed = false;
        PreparedStatement ps = null;
        try  {
            ps = connection.prepareStatement(DataBaseConstants.UPDATE_BANK_ACCOUNT);
            ps.setDouble(1, bankAccount.getBankAccountAmount());
            ps.setString(2, bankAccount.getBankAccountCurrency());
            ps.setInt(3, bankAccount.getUserID());
            ps.setInt(4,bankAccount.getBankAccountID());
    
            logger.debug(ps.toString());
            
            ps.execute();
            executed = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return executed;
    }
    
}
