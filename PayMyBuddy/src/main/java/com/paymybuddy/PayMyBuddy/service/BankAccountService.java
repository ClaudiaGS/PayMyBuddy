package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.repository.BankAccountRepository;
import com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

@Service
public class BankAccountService implements IBankAccountService {
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    UserService userService;
    private static final Logger logger = LogManager.getLogger("BankAccountService");
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService#createBankAccount(Connection,BankAccount)
     */
    @Override
    public boolean createBankAccount(Connection connection,BankAccount bankAccount) {
        if (userService.readUser(bankAccount.getUserID()) != null) {
            bankAccountRepository.createBankAccount(connection,bankAccount);
        }
        return bankAccountRepository.createBankAccount(connection,bankAccount);
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService#readBankAccount(int)
     */
    @Override
    public BankAccount readBankAccount(int bankAccountID) {
        BankAccount bankAccount = bankAccountRepository.readBankAccount(bankAccountID);
        logger.info("Bank account with id " + bankAccountID + " is " + bankAccount);
        return bankAccount;
    }
    
    /**
     * (non-javadoc)
     *
     * @see IBankAccountService#readBankAccountList()
     */
    @Override
    public List<BankAccount> readBankAccountList() {
        List<BankAccount> bankAccountList = bankAccountRepository.readBankAccountList();
        logger.info("Bank account list is " + bankAccountList);
        return bankAccountList;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService#readUsersBankAccount(int)
     */
    @Override
    public BankAccount readUsersBankAccount(int userID) {
        BankAccount bankAccount = bankAccountRepository.readUsersBankAccount(userID);
        logger.info("Bank account for user with id " + userID + " is " + bankAccount);
        return bankAccount;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService#updateAmount(int, double, String)
     */
    @Override
    public double updateAmount(int userID, double transferedAmount, String operation) {
        double amount = bankAccountRepository.updateAmount(userID, transferedAmount, operation);
        return amount;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService#updateBankAccount(java.sql.Connection, int, java.util.HashMap)
     */
    @Override
    public boolean updateBankAccount(Connection connection, int bankAccountID, HashMap<String, Object> params) {
        
        BankAccount bankAccount = this.bankAccountRepository.readBankAccount(bankAccountID);
        
        bankAccount.setBankAccountAmount((Double) params.getOrDefault("bankAccountAmount", (bankAccount.getBankAccountAmount())));
        bankAccount.setBankAccountCurrency((String) params.getOrDefault("bankAccountCurrency", bankAccount.getBankAccountCurrency()));
        bankAccount.setUserID((Integer) params.getOrDefault("userID", bankAccount.getUserID()));
        
        return bankAccountRepository.updateBankAccount(connection, bankAccount);
    }
}
