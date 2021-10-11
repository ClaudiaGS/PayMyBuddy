package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Contact;
import com.paymybuddy.PayMyBuddy.repository.interfaces.IContactRepository;
import com.paymybuddy.PayMyBuddy.service.interfaces.IContactService;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService implements IContactService {
    @Autowired
    IContactRepository contactRepository;
    @Autowired
    IUserService userService;
    private static final Logger logger = LogManager.getLogger("ContactService");
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.service.interfaces.IContactService#createContact(Contact)
     */
    @Override
    public boolean createContact(Contact contact) {
        boolean result=false;
        if(userService.createUser(userService.readUser(contact.getUserIDAccount()))) {
            contactRepository.createContact(contact);
            logger.info("Contact "+contact+" created");
            result=true;
        }else
        {
            logger.error("Contact "+contact+" cannot be created");
        }
        
        return result;
    }
    
    /**
     * (non-javadoc)
     *
     * @see IContactService#readContactList()
     */
    @Override
    public List<Contact> readContactList() {
        List<Contact>contactList=contactRepository.readContactList();
        logger.info("Contact list is "+contactList);
        return contactList;
    }
    
    /**
     * (non-javadoc)
     *
     * @see IContactService#readUsersContactList(int)
     */
    @Override
    public List<Contact> readUsersContactList(int userIDAccount) {
        List<Contact>contactList=contactRepository.readUsersContactList(userIDAccount);
        logger.info("Contact list for user "+userIDAccount+" is "+contactList);
        return contactList;
    }
    
    /**
     * (non-javadoc)
     *
     * @see IContactService#readContact(int)
     */
    @Override
    public Contact readContact(int contactID) {
        Contact contact=contactRepository.readContact(contactID);
        logger.info("Contact with id "+contactID+" is "+contact);
        return contact;
    }
    
    
    /**
     * (non-javadoc)
     *
     * @see IContactService#deleteContact(int)
     */
    @Override
    public boolean deleteContact(int contactID) {
        logger.info("Deleted contact with id "+contactID);
        return contactRepository.deleteContact(contactID);
    }
}
