package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.model.Contact;
import com.paymybuddy.PayMyBuddy.model.ContactForTransaction;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.service.interfaces.IAccountService;
import com.paymybuddy.PayMyBuddy.service.interfaces.IContactForTransactionService;
import com.paymybuddy.PayMyBuddy.service.interfaces.IContactService;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactForTransactionService implements IContactForTransactionService {
    @Autowired
    IUserService userService;
    @Autowired
    IAccountService accountService;
    @Autowired
    IContactService contactService;
    
    /**
     * (non-javadoc)
     *
     * @see IContactForTransactionService#getContactForTransactionList(int)
     */
    public List<ContactForTransaction> getContactForTransactionList(final int userID){
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
     * @see IContactForTransactionService#readContactForTransaction(int)
     */
    @Override
    public ContactForTransaction readContactForTransaction(final int contactID) {
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
