package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.Contact;
import com.paymybuddy.PayMyBuddy.repository.ContactRepository;
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
    
    @Override
    public Contact createContact(int userIDAccount, int userIDContact) {
        Contact contact = null;
        if(userService.createUser(userService.readUser(userIDAccount))) {
            contact = contactRepository.createContact(userIDAccount,userIDContact);
        }
        return contact;
    }
    @Override
    public List<Contact> readContactList() {
        return contactRepository.readContactList();
    }
    
    @Override
    public List<Contact> readUsersContactList(int userIDAccount) {
        return contactRepository.readUsersContactList(userIDAccount);
    }
    
    @Override
    public Contact readContact(int contactID) {
        return contactRepository.readContact(contactID);
    }
    
    @Override
    public boolean updateContact(int contactID, HashMap<String, Object> params) {
        return contactRepository.updateContact(contactID,params);
    }
    
    @Override
    public boolean deleteContact(int contactID) {
        return contactRepository.deleteContact(contactID);
    }
}
