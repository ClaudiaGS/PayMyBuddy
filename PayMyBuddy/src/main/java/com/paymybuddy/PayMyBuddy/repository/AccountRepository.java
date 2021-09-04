package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.config.DataBase;
import com.paymybuddy.PayMyBuddy.constants.DataBaseConstants;
import com.paymybuddy.PayMyBuddy.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountRepository implements IAccountRepository {
    @Autowired
    Account account;
    private static final Logger logger = LogManager.getLogger("AccountRepository");
    @Autowired
    public DataBase dataBase;
    
    @Autowired
    UserRepository userRepository;
    
    @Override
    public Account createAccount(int userID, String email, String password) {
        logger.info("Create account with email: " + email + " for user with id: " + userID);
        account = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.CREATE_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setInt(3, userID);
            ps.execute();
            resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                account.setAccountID(resultSet.getInt(1));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(resultSet);
            dataBase.closePreparedStatement(ps);
        }
        return account;
    }
    
    @Override
    public Account readAccount(int accountID) {
        logger.info("Read account with id " + accountID);
        account = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_ACCOUNT);
            ps.setInt(1, accountID);
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
    
    protected Account processRow(ResultSet rs) throws SQLException {
        account=null;
        account.setAccountID(rs.getInt(1));
        account.setAccountEmail(rs.getString(2));
        account.setAccountPassword(rs.getString(3));
        return account;
    }
    
    public List<Account> readAccountList() {
        logger.info("Read account list from table");
        List<Account> accountList = new ArrayList<Account>();
        account = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_ACCOUNT_LIST);
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
    
    @Override
    public boolean updateAccount(int accountID, HashMap<String, Object> params) {
        logger.info("Update account with id " + accountID);
        boolean executed = false;
        account = readAccount(accountID);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case "accountEmail":
                    account.setAccountEmail(entry.getValue().toString());
                    break;
                case "accountPassword":
                    account.setAccountPassword(entry.getValue().toString());
                    break;
                case "userID":
                    account.setUserID((int) entry.getValue());
                    break;
                default:
                    logger.warn("Trying to modify unexisting account parameter");
                    break;
            }
        }
        PreparedStatement ps = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.UPDATE_ACCOUNT);
            ps.setString(1, account.getAccountEmail());
            ps.setString(2, account.getAccountPassword());
            ps.setInt(5, account.getUserID());
            ps.execute();
            executed = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closePreparedStatement(ps);
        }
        return executed;
    }
}

