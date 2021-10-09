package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.constants.DataBaseConstants;
import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.repository.interfaces.IAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository implements IAccountRepository {
    @Autowired
    Account account;
    private static final Logger logger = LogManager.getLogger("AccountRepository");
    @Autowired
    public IDataBase dataBase;

    
    private Account processRow(ResultSet rs) throws SQLException {
        account = new Account();
        account.setAccountID(rs.getInt(1));
        account.setAccountEmail(rs.getString(2));
        account.setAccountPassword(rs.getString(3));
        account.setUserID(rs.getInt(4));
        return account;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.repository.interfaces.IAccountRepository#createAccount(Connection, Account)
     */
    @Override
    public boolean createAccount(Connection connection, Account account) {
        boolean result = false;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = connection.prepareStatement(DataBaseConstants.CREATE_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getAccountEmail());
            ps.setString(2, account.getAccountPassword());
            ps.setInt(3, account.getUserID());
            
            logger.debug(ps.toString());
            
            ps.execute();
            
            resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                account.setAccountID(resultSet.getInt(1));
                result = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }
    
    /**
     * (non-javadoc)
     *
     * @see IAccountRepository#readAccountList()
     */
    public List<Account> readAccountList() {
        List<Account> accountList = new ArrayList<Account>();
        account = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_ACCOUNT_LIST);
            
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            while (rs.next()) {
                account = processRow(rs);
                accountList.add(account);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        return accountList;
        
    }
    
    /**
     * (non-javadoc)
     *
     * @see IAccountRepository#readAccount(int)
     */
    @Override
    public Account readAccount(int accountID) {
        account = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_ACCOUNT);
            ps.setInt(1, accountID);
            
            logger.debug(ps.toString());
            
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                account = processRow(resultSet);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            dataBase.closeResultSet(resultSet);
            dataBase.closePreparedStatement(ps);
            return account;
        }
    }
    
    /**
     * (non-javadoc)
     *
     * @see IAccountRepository#readUsersAccount(int)
     */
    @Override
    public Account readUsersAccount(int userID) {
        account = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_USERS_ACCOUNT);
            ps.setInt(1, userID);
            
            logger.debug(ps.toString());
            
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                account = processRow(resultSet);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            dataBase.closeResultSet(resultSet);
            dataBase.closePreparedStatement(ps);
            return account;
        }
    }
    
    /**
     * (non-javadoc)
     *
     * @see IAccountRepository#updateAccount(Account)
     */
    @Override
    public boolean updateAccount(final Account account) {
        boolean executed = false;
        
        PreparedStatement ps = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.UPDATE_ACCOUNT);
            ps.setInt(4, account.getAccountID());
            ps.setString(1, account.getAccountEmail());
            ps.setString(2, account.getAccountPassword());
            ps.setInt(3, account.getUserID());
            
            logger.debug(ps.toString());
            
            ps.execute();
            executed = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closePreparedStatement(ps);
        }
        return executed;
    }
    
    /**
     * (non-javadoc)
     *
     * @see IAccountRepository#authenticate(Account)
     */
    @Override
    public Account authenticate(Account account) {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Account accountToAuthentificate = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.AUTHENTICATE);
            ps.setString(1, account.getAccountEmail());
            ps.setString(2, account.getAccountPassword());
            
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            if (rs.next()) {
                accountToAuthentificate = new Account();
                accountToAuthentificate.setAccountID(rs.getInt(1));
                accountToAuthentificate.setAccountEmail(rs.getString(2));
                accountToAuthentificate.setAccountPassword(rs.getString(3));
                accountToAuthentificate.setUserID(rs.getInt(4));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        return accountToAuthentificate;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.repository.interfaces.IAccountRepository#alreadyExist(String)
     */
    @Override
    public boolean alreadyExist(final String email) {
        boolean result = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try (Connection con = dataBase.getConnection()) {
            
            ps = con.prepareStatement(DataBaseConstants.ACCOUNT_EXIST);
            ps.setString(1, email);
            
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                result = true;
            }
            
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        
        return result;
    }
}

