package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.config.DataBase;
import com.paymybuddy.PayMyBuddy.constants.DataBaseConstants;
import com.paymybuddy.PayMyBuddy.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class AccountRepository implements IAccountRepository{
    @Autowired
    Account account;
    private static final Logger logger = LogManager.getLogger("AccountRepository");
    @Autowired
    public DataBase dataBaseConfig;
    
    @Autowired
    UserRepository userRepository;
    @Override
    public Account createAccount(int userID,String email, String password) {
//        List<User>userList=userRepository.readUserList();
//        newAccount.setUserID(userList.get(userList.size()-1).getUserID()+1);
        
        Account newAccount=new Account();
        Connection con = null;
        try {
            
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DataBaseConstants.CREATE_ACCOUNT);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setInt(3, userID);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        finally {
            newAccount.setAccountEmail(email);
            newAccount.setAccountPassword(password);
            newAccount.setUserID(newUser.getUserID());
        }
        return newAccount;
    }
    
    //    public Account createAccount(Account newAccount) {
////        List<User>userList=userRepository.readUserList();
////        newAccount.setUserID(userList.get(userList.size()-1).getUserID()+1);
//        logger.info("Create account");
//        Connection con = null;
//        try {
//            con = dataBaseConfig.getConnection();
//            PreparedStatement ps = con.prepareStatement(DataBaseConstants.CREATE_ACCOUNT);
//            ps.setString(1, newAccount.getAccountEmail());
//            ps.setString(2, newAccount.getAccountPassword());
//      //      ps.setInt(3, newAccount.getUserID());
//            ps.execute();
//            ps.close();
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//        return newAccount;
//    }
//
    @Override
    public Account readAccount(int accountID) {
        logger.info("Read account with id "+accountID);
        account=null;
        Connection con = null;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DataBaseConstants.GET_ACCOUNT);
            ps.setString(1, String.valueOf(accountID));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = processRow(rs);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            dataBaseConfig.closeConnection(con);
            return account;
        }
    }
    protected Account processRow(ResultSet rs) throws SQLException {
        account.setAccountID(rs.getInt(1));
        account.setAccountEmail(rs.getString(2));
        account.setAccountPassword(rs.getString(3));
        return account;
    }
    
    public List<Account> readAccountList() {
        logger.info("Read account list from table");
        List<Account> accountList = new ArrayList<Account>();
        account=null;
        Connection con = null;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DataBaseConstants.GET_ACCOUNT_LIST);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                account = processRow(rs);
                accountList.add(account);
            }
        } catch (Exception e) {
           logger.error(e.getMessage());
        } finally {
            dataBaseConfig.closeConnection(con);
        }
        return accountList;
        
    }
    
    @Override
    public Account updateAccount(int accountID, HashMap<String, String> params) {
        logger.info("Update account with id "+accountID);
        return null;
    }
}
