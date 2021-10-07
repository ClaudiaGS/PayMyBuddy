package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.Contact;
import com.paymybuddy.PayMyBuddy.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {
    @Autowired
    ContactService contactService;
    
    @PostMapping("/createContact")
    public boolean createContact(@RequestParam int userIDAccount, @RequestParam int userIDContact){
        Contact contact=new Contact();
        contact.setUserIDAccount(userIDAccount);
        contact.setUserIDContact(userIDContact);
        return contactService.createContact(contact);
    }
    @GetMapping("/readContactInfo")
    public Contact readContact(@RequestParam int contactID){
        return contactService.readContact(contactID);
    }
    @GetMapping("/readContactListInfo")
    public List<Contact> readContactList(){
        return contactService.readContactList();
    }
    @GetMapping("/readUsersContactListInfo")
    public List<Contact> readUsersContactList(@RequestParam int userIDAccount) {
    return contactService.readUsersContactList(userIDAccount);
    
    }
    
    @DeleteMapping("/deleteContact")
    public boolean deleteContact(@RequestParam int contactID){
        return contactService.deleteContact(contactID);
}

}
