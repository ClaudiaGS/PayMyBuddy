package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.ContactView;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.model.TransactionView;
import com.paymybuddy.PayMyBuddy.service.interfaces.IContactViewService;
import com.paymybuddy.PayMyBuddy.service.interfaces.ITransactionService;
import com.paymybuddy.PayMyBuddy.service.interfaces.ITransactionViewService;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionViewService implements ITransactionViewService {
    
    @Autowired
    ITransactionService transactionService;
    @Autowired
    IContactViewService contactViewService;
    @Autowired
    IUserService userService;
    
    private static final Logger logger = LogManager.getLogger("TransactionViewService");
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.ITransactionViewService#getTransactionViewList(int)
     */
    @Override
    public List<TransactionView> getTransactionViewList(final int userID) {
        logger.info("Get transactions for user with id:" + userID);
        List<TransactionView> transactionViewList = new ArrayList<>();
        List<Transaction> transactionList = transactionService.readUsersTransactionList(userID);
    
        for (Transaction transaction : transactionList) {
            TransactionView transactionView = new TransactionView();
            if (transaction.getUserIDSender() == userID) {
                transactionView.setTransactionID(transaction.getTransactionID());
                for (ContactView cv:contactViewService.getContactViewList(userID)) {
                    if (cv.getFirstName().equals(transactionView.getFirstName()) && cv.getLastName().equals(transactionView.getLastName())) {
                        transactionView.setContactID(cv.getContactID());
    
                    }
                }
                transactionView.setFirstName(userService.readUser(transaction.getUserIDReceiver()).getUserFirstName());
                transactionView.setLastName(userService.readUser(transaction.getUserIDReceiver()).getUserLastName());
                transactionView.setDescription(transaction.getTransactionDescription());
                transactionView.setAmount(transaction.getTransactionDebitedAmount());
            }
            transactionViewList.add(transactionView);
        }
        return transactionViewList;
    }
}
