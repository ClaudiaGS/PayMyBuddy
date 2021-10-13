package com.paymybuddy.PayMyBuddy.service.interfaces;

import com.paymybuddy.PayMyBuddy.model.ContactForTransaction;

import java.util.List;

public interface IContactForTransactionService {
    
    /**
     *
     * @param contactID
     * @return ContactForTransaction
     */
    public ContactForTransaction readContactForTransaction(final int contactID);
    
    /**
     *
     * @param userID
     * @return List<ContactForTransaction>
     */
    public List<ContactForTransaction> getContactForTransactionList(final int userID);
}
