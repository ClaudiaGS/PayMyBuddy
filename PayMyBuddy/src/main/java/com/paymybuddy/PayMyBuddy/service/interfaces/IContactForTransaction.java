package com.paymybuddy.PayMyBuddy.service.interfaces;

import com.paymybuddy.PayMyBuddy.model.ContactForTransaction;

import java.util.List;

public interface IContactForTransaction {
    
    /**
     *
     * @param contactID
     * @return ContactForTransaction
     */
    public ContactForTransaction readContactForTransaction(int contactID);
    
    /**
     *
     * @param userID
     * @return List<ContactForTransaction>
     */
    public List<ContactForTransaction> getContactForTransactionList(int userID);
}
