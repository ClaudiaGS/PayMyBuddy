package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.*;
import com.paymybuddy.PayMyBuddy.service.interfaces.IContactViewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactViewService  implements IContactViewService {
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;
    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    ContactService contactService;
    private static final Logger logger = LogManager.getLogger("ContactViewService");
    
    public List<ContactView> getContactViewList(int userID){
        
        logger.info("Get contacts for user " + userID);
        List<ContactView> contactViewList = new ArrayList<ContactView>();
        List<User> userList = userService.readUserList();
        List<Account> accountList = accountService.readAccountList();
        List<BankAccount> bankAccountList = bankAccountService.readBankAccountList();
        List<Contact> contactList = contactService.readUsersContactList(userID);
        
        for (Contact contact : contactList) {
            ContactView contactView = new ContactView();
            for (User user : userList) {
                if (user.getUserID() == contact.getUserIDContact()) {
                    contactView.setContactID(contact.getContactID());
                    contactView.setFirstName(user.getUserFirstName());
                    contactView.setLastName(user.getUserLastName());
                }
            }
            for (Account account : accountList) {
                if (account.getUserID() == contact.getUserIDContact()) {
                    contactView.setEmail(account.getAccountEmail());
                }
            }
            for (BankAccount bankAccount : bankAccountList) {
                if (bankAccount.getUserID() == contact.getUserIDContact()) {
                    contactView.setBankAccountNumber(bankAccount.getBankAccountNumber());
                }
            }
            contactViewList.add(contactView);
            
        }
        
        
        return contactViewList;
    }

//    public boolean deleteContactView(int contactID) {
//        logger.info("Delete contact with ID " + contactID);
//        boolean deleted = false;
//        Contact contact = contactService.readContact(contactID);
//        List<ContactView> contactViewList = new ArrayList<ContactView>();
//
//        if (contactViewList == null) return true;
//
//        for (ContactView contactView : contactViewList) {
//            if (contactView.getContactID() == contactID) {
//               // getContactViewList(contact.getUserIDAccount()).remove(contactView);
//                contactService.deleteContact(contactID);
//
//                deleted = true;
//            }
//        }
//        return deleted;
//
//    }
    
    
}




