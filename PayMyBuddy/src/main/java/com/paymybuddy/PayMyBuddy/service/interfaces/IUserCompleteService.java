package com.paymybuddy.PayMyBuddy.service.interfaces;

import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.model.UserComplete;

import java.util.List;

public interface IUserCompleteService {
    
    /**
     * @param account
     * @return UserComplete
     */
    public UserComplete login(Account account);
    
    /**
     * @return List<UserComplete>
     */
    public List<UserComplete> readUserCompleteList();
    
    /**
     * @param userID
     * @return UserComplete
     */
    public UserComplete readUserComplete(int userID);
}
