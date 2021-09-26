package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Contact;
import com.paymybuddy.PayMyBuddy.repository.ContactRepository;
import com.paymybuddy.PayMyBuddy.service.interfaces.IContactService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ContactService implements IContactService {
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    UserService userService;
    private static final Logger logger = LogManager.getLogger("ContactService");
    @Override
    public Contact createContact(int userIDAccount, int userIDContact) {
        Contact contact = null;
        if(userService.createUser(userService.readUser(userIDAccount))) {
            contact = contactRepository.createContact(userIDAccount,userIDContact);
        }
        if(contact!=null) {
            logger.info("Created contact " + contact);
        }else{
            logger.info("Contact not created");
        }
        return contact;
    }
    @Override
    public List<Contact> readContactList() {
        List<Contact>contactList=contactRepository.readContactList();
        logger.info("Contact list is "+contactList);
        return contactList;
    }
    
    @Override
    public List<Contact> readUsersContactList(int userIDAccount) {
        List<Contact>contactList=contactRepository.readUsersContactList(userIDAccount);
        logger.info("Contact list for user "+userIDAccount+" is "+contactList);
        return contactList;
    }
    
    @Override
    public Contact readContact(int contactID) {
        Contact contact=contactRepository.readContact(contactID);
        logger.info("Contact with id "+contactID+" is "+contact);
        return contact;
    }
    
    @Override
    public boolean updateContact(int contactID, HashMap<String, Object> params) {
        return contactRepository.updateContact(contactID,params);
    }
    
    @Override
    public boolean deleteContact(int contactID) {
        logger.info("Deleted contact with id "+contactID);
        logger.info("Contact list after delete is "+contactRepository.readContactList());
        return contactRepository.deleteContact(contactID);
    }
}
