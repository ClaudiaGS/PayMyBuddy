package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.repository.interfaces.IBankAccountRepository;
import com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserService;
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
    IBankAccountRepository bankAccountRepository;
    @Autowired
    IUserService userService;
    private static final Logger logger = LogManager.getLogger("BankAccountService");
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService#createBankAccount(Connection, BankAccount)
     */
    @Override
    public boolean createBankAccount(Connection connection, final BankAccount bankAccount) {
        if (userService.readUser(bankAccount.getUserID()) != null) {
            bankAccountRepository.createBankAccount(connection, bankAccount);
        }
        return bankAccountRepository.createBankAccount(connection, bankAccount);
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService#readBankAccount(int)
     */
    @Override
    public BankAccount readBankAccount(final int bankAccountID) {
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
    public BankAccount readUsersBankAccount(final int userID) {
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
    public double updateAmount(final int userID,final double transferedAmount,final String operation) {
        
        double bankAccountAmount = readUsersBankAccount(userID).getBankAccountAmount();
        switch (operation) {
            case "add":
                bankAccountAmount = bankAccountAmount + transferedAmount;
                break;
            case "subtract":
                if (bankAccountAmount >= (transferedAmount + 0.5 / 100 * transferedAmount)) {
                    bankAccountAmount = bankAccountAmount - (transferedAmount + 0.5 / 100 * transferedAmount);
                } else {
                    logger.error("Not enough money in your bank account! You have " + bankAccountAmount + " euros available.");
                }
                break;
            default:
                logger.error("Operation unknown.You have to add or substract money");
        }
        logger.info("Amount after update is " + bankAccountAmount);
        return bankAccountAmount;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService#updateAmountPersonalAccount(double, double, String)
     */
    @Override
    public double updateAmountPersonalAccount(double amount, final double amountForOperation,final String operation) {
        
        switch (operation) {
            case "add":
                amount += amountForOperation;
                break;
            case "subtract":
                if (amountForOperation <= amount) {
                    amount -= amountForOperation;
                }else{
                    logger.error("Cannot withdraw.Not enough money");
                }
                break;
            default:
                logger.error("Operation unknown.You have to add or substract money");
        }
        logger.info("Amount after update is " + amount);
        return amount;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService#updateBankAccount(java.sql.Connection, int, java.util.HashMap)
     */
    @Override
    public boolean updateBankAccount(Connection connection,final int bankAccountID,final HashMap<String, Object> params) {
        
        BankAccount bankAccount = this.bankAccountRepository.readBankAccount(bankAccountID);
        
        bankAccount.setBankAccountAmount((Double) params.getOrDefault("bankAccountAmount", (bankAccount.getBankAccountAmount())));
        bankAccount.setBankAccountCurrency((String) params.getOrDefault("bankAccountCurrency", bankAccount.getBankAccountCurrency()));
        bankAccount.setUserID((Integer) params.getOrDefault("userID", bankAccount.getUserID()));
        
        return bankAccountRepository.updateBankAccount(connection, bankAccount);
    }
}
