package com.paymybuddy.PayMyBuddy.repository.interfaces;

import com.paymybuddy.PayMyBuddy.model.Contact;

import java.util.List;

public interface IContactRepository {
    /**
     *
     * @param contact
     * @return boolean
     */
    public boolean createContact(Contact contact);
    
    
    /**
     *
     * @return List<Contact>
     */
    public List<Contact> readContactList();
    
    
    /**
     *
     * @param userIDAccount
     * @return List<Contact>
     */
    public List<Contact> readUsersContactList(int userIDAccount);
    
    
    /**
     *
     * @param contactID
     * @return Contact
     */
    public Contact readContact(int contactID);
    
    
    /**
     *
     * @param contact
     * @return boolean
     */
    public boolean updateContact(Contact contact);
    
    
    /**
     *
     * @param contactID
     * @return boolean
     */
    public boolean deleteContact(int contactID);
}

