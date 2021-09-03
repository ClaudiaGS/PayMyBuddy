package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.config.DataBase;
import com.paymybuddy.PayMyBuddy.constants.DataBaseConstants;
import com.paymybuddy.PayMyBuddy.model.BankAccount;
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
import java.util.Map;

@Repository
public class BankAccountRepository implements IBankAccountRepository {
    @Autowired
    BankAccount bankAccount;
    
    private static final Logger logger = LogManager.getLogger("BankAccountRepository");
    @Autowired
    public DataBase dataBaseConfig;
    
    public BankAccount readBankAccount(int bankAccountID) {
        Connection con = null;
        bankAccount = null;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DataBaseConstants.READ_BANK_ACCOUNT);
            ps.setString(1, String.valueOf(bankAccountID));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bankAccount = processRow(rs);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            dataBaseConfig.closeConnection(con);
            return bankAccount;
        }
    }
    public List<BankAccount> readBankAccountList() {
        List<BankAccount> bankAccountList = new ArrayList<BankAccount>();
        String sql = "SELECT * FROM bank_account";
        bankAccount = null;
        Connection con = null;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DataBaseConstants.READ_BANK_ACCOUNT_LIST);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bankAccount = processRow(rs);
                bankAccountList.add(bankAccount);
                
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBaseConfig.closeConnection(con);
        }
        return bankAccountList;
        
    }
    
    protected BankAccount processRow(ResultSet rs) throws SQLException {
        bankAccount = null;
        bankAccount.setBankAccountID(rs.getInt(1));
        bankAccount.setBankAccountNumber(rs.getInt(2));
        bankAccount.setBankAccountAmount(rs.getDouble(3));
        bankAccount.setBankAccountCurrency(rs.getString(4));
        bankAccount.setUserID(rs.getInt(5));
        return bankAccount;
    }
    
    public BankAccount updateBankAccount(int bankAccountID, HashMap<String, Object> params) {
        bankAccount = readBankAccount(bankAccountID);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case "bankAccountNumber":
                    bankAccount.setBankAccountNumber((int) entry.getValue());
                    break;
                case "bankAccountAmount":
                    bankAccount.setBankAccountAmount((double) entry.getValue());
                    break;
                case "bankAccountCurrency":
                    bankAccount.setBankAccountCurrency(entry.getValue().toString());
                    break;
                case "userID":
                    bankAccount.setUserID((int) entry.getValue());
                default:
                    logger.warn("Trying to modify unexisting bank account parameter");
                    break;
            }
        }
        
        Connection con = null;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DataBaseConstants.UPDATE_BANK_ACCOUNT);
            ps.setInt(1, bankAccount.getBankAccountNumber());
            ps.setDouble(2, bankAccount.getBankAccountAmount());
            ps.setString(3, bankAccount.getBankAccountCurrency());
            ps.setInt(4, bankAccount.getUserID());
            ps.execute();
            ps.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return bankAccount;
    }
    
}
