package com.paymybuddy.PayMyBuddy.service.interfaces;

import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.model.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
    
    /**
     *
     * @param newUser
     * @return boolean
     */
    public boolean createUser(final User newUser);
    
    /**
     *
     * @return List<User>
     */
    public List<User> readUserList();
    
    /**
     *
     * @param userID
     * @return User
     */
    public User readUser(final int userID);
    
    /**
     *
     * @param userID
     * @param params
     * @return boolean
     */
    public boolean updateUser(final int userID, final Map<String, Object> params);
    
    /**
     *
     * @param account
     * @param user
     * @return int
     */
    public boolean register(final Account account, final User user);
    

}
