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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BankAccountRepository implements IBankAccountRepository {
    @Autowired
    public DataBase dataBase;
    @Autowired
    BankAccount bankAccount;
    
    private static final Logger logger = LogManager.getLogger("BankAccountRepository");
    
    @Override
    public BankAccount createBankAccount(BankAccount bankAccount, int userID) {
        logger.info("Creating bank account for user with id " + userID);
        bankAccount = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.CREATE_BANK_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, bankAccount.getBankAccountNumber());
            ps.setDouble(2, bankAccount.getBankAccountAmount());
            ps.setString(3, bankAccount.getBankAccountCurrency());
            ps.execute();
            resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                bankAccount.setBankAccountID(resultSet.getInt(1));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(resultSet);
            dataBase.closePreparedStatement(ps);
        }
        return bankAccount;
    }
    
    @Override
    public BankAccount readBankAccount(int bankAccountID) {
        logger.info("Read bank account info for bank account ID: " + bankAccountID);
        bankAccount = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_BANK_ACCOUNT);
            ps.setInt(1, bankAccountID);
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
    @Override
    public List<BankAccount> readBankAccountList() {
        logger.info("Reading bank account list from table");
        List<BankAccount> bankAccountList = new ArrayList<BankAccount>();
        bankAccount = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_BANK_ACCOUNT_LIST);
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
    @Override
    public BankAccount readUsersBankAccount(int userID){
        logger.info("Read bank account info for user with ID: " + userID);
        bankAccount = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_USERS_BANK_ACCOUNT);
            ps.setInt(1, userID);
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
    
    
    protected BankAccount processRow(ResultSet rs) throws SQLException {
        bankAccount=new BankAccount();
        bankAccount.setBankAccountID(rs.getInt(1));
        bankAccount.setBankAccountNumber(rs.getInt(2));
        bankAccount.setBankAccountAmount(rs.getDouble(3));
        bankAccount.setBankAccountCurrency(rs.getString(4));
        bankAccount.setUserID(rs.getInt(5));
        return bankAccount;
    }
    @Override
    public double updateAmount(int userID, double transferedAmount, String operation ){
        double bankAccountAmount=readUsersBankAccount(userID).getBankAccountAmount();
        switch (operation){
            case "add":
                bankAccountAmount=bankAccountAmount+transferedAmount;
                break;
            case "substract":
                if(bankAccountAmount>=(transferedAmount+0.5/100*transferedAmount)) {
                    bankAccountAmount = bankAccountAmount - (transferedAmount + 0.5 / 100 * transferedAmount);
                }else{
                    logger.error("Not enough money in your bank account! You have "+bankAccountAmount+ " euros available.");
                }
                break;
            case "default":
                logger.error("Operation unknown.You have to add or substract money");
        }
        logger.info("Amount after update is "+bankAccountAmount);
        return bankAccountAmount;
    }
    
    @Override
    public boolean updateBankAccount(int bankAccountID, HashMap<String,String> params) {
        logger.info("Updating bank account with ID: " + bankAccountID);
        boolean executed = false;
        bankAccount = readBankAccount(bankAccountID);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case "bankAccountNumber":
                    bankAccount.setBankAccountNumber( Integer.parseInt(entry.getValue()));
                    break;
                case "bankAccountAmount":
                    bankAccount.setBankAccountAmount( Double.parseDouble(entry.getValue()));
                    break;
                case "bankAccountCurrency":
                    bankAccount.setBankAccountCurrency(entry.getValue().toString());
                    break;
                case "userID":
                    bankAccount.setUserID( Integer.parseInt(entry.getValue()));
                default:
                    logger.warn("Trying to modify unexisting bank account parameter");
                    break;
            }
        }
        PreparedStatement ps = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.UPDATE_BANK_ACCOUNT);
            ps.setInt(1, bankAccount.getBankAccountNumber());
            ps.setDouble(2, bankAccount.getBankAccountAmount());
            ps.setString(3, bankAccount.getBankAccountCurrency());
            ps.setInt(4, bankAccount.getUserID());
            ps.setInt(5,bankAccountID);
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
