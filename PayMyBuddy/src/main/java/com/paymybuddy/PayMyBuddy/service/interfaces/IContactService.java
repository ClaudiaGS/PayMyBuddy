package com.paymybuddy.PayMyBuddy.service.interfaces;

import com.paymybuddy.PayMyBuddy.model.Contact;

import java.util.List;

public interface IContactService {
    
    
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
     * @param contactID
     * @return boolean
     */
    public boolean deleteContact(int contactID);
}
