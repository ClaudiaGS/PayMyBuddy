package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.*;
import com.paymybuddy.PayMyBuddy.service.interfaces.IContactForTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactForTransactionService implements IContactForTransaction {
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;
    @Autowired
    ContactService contactService;
    
    /**
     * (non-javadoc)
     *
     * @see IContactForTransaction#getContactForTransactionList(int)
     */
    public List<ContactForTransaction> getContactForTransactionList(int userID){
        List<ContactForTransaction> contactForTransactionList = new ArrayList<ContactForTransaction>();
        
        List<Contact> contactList = contactService.readUsersContactList(userID);
        
        for (Contact contact : contactList) {
            ContactForTransaction contactForTransaction = readContactForTransaction(contact.getContactID());
            
            contactForTransactionList.add(contactForTransaction);
            
        }
        
        return contactForTransactionList;
    }
    
    /**
     * (non-javadoc)
     *
     * @see IContactForTransaction#readContactForTransaction(int)
     */
    @Override
    public ContactForTransaction readContactForTransaction(int contactID) {
        Contact contact= contactService.readContact(contactID);
        User user=userService.readUser(contact.getUserIDContact());
        Account account=accountService.readUsersAccount(contact.getUserIDContact());
        ContactForTransaction contactForTransaction=new ContactForTransaction();
        contactForTransaction.setContactID(contactID);
        contactForTransaction.setEmail(account.getAccountEmail());
        contactForTransaction.setFirstName(user.getUserFirstName());
        contactForTransaction.setLastName(user.getUserLastName());
        
        return contactForTransaction;
    }
}
