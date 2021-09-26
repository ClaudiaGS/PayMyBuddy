package com.paymybuddy.PayMyBuddy.service.interfaces;

import com.paymybuddy.PayMyBuddy.model.Contact;

import java.util.HashMap;
import java.util.List;

public interface IContactService {
    public Contact createContact(int userIDAccount, int userIDContact);
    public List<Contact> readContactList();
    public List<Contact> readUsersContactList(int userIDAccount);
    public Contact readContact(int contactID);
    public boolean updateContact(int contactID, HashMap<String, Object> params);
    public boolean deleteContact(int contactID);
}
