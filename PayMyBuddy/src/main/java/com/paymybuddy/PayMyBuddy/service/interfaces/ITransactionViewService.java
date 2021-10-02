package com.paymybuddy.PayMyBuddy.service.interfaces;

import com.paymybuddy.PayMyBuddy.model.TransactionView;

import java.util.List;

public interface ITransactionViewService {
    
    /**
     *
     * @param userID
     * @return List<TransactionView>
     */
    public List<TransactionView> getTransactionViewList(int userID);
}
