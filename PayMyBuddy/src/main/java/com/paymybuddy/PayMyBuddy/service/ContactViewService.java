package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.*;
import com.paymybuddy.PayMyBuddy.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactViewService  implements IContactViewService {
    @Autowired
    IUserService userService;
    @Autowired
    IAccountService accountService;
    @Autowired
    IBankAccountService bankAccountService;
    @Autowired
    IContactService contactService;

    
    /**
     * (non-javadoc)
     *
     * @see IContactViewService#getContactViewList(int)
     */
    public List<ContactView> getContactViewList(int userID){
        
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
                    contactView.setBankAccountID(bankAccount.getBankAccountID());
                }
            }
            contactViewList.add(contactView);
            
        }
        
        return contactViewList;
    }
    
}




