package com.paymybuddy.PayMyBuddy.repository.interfaces;

import com.paymybuddy.PayMyBuddy.model.User;

import java.util.List;

public interface IUserRepository {
    
    /**
     *
     * @param user
     * @return boolean
     */
    public boolean createUser(final User user);
    
    /**
     *
     * @return List<User>
     */
    public List<User> readUserList();
    
    /**
     *
     * @param id
     * @return User
     */
    public User readUser(final int id);
    
    /**
     *
     * @param user
     * @return boolean
     */
    public boolean updateUser(final User user);

}