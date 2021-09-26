package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.*;
import com.paymybuddy.PayMyBuddy.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewController {
    @Autowired
    UserCompleteService userCompleteService;
    @Autowired
    AccountService accountService;
    @Autowired
    ContactService contactService;
    @Autowired
    ContactViewService contactViewService;
    @Autowired
    TransactionViewService transactionViewService;
    @Autowired
    TransactionService transactionService;
    
    HttpSession session=null;
    
    private static final Logger logger = LogManager.getLogger("ViewController");
    
    @GetMapping("/login")
    public String connexion(Model model, Account account) {
        
        model.addAttribute("account", account);
        return "Login";
    }
    
    @PostMapping("/home")
    public String home(@ModelAttribute Account account, Model model, HttpServletRequest request) {
        UserComplete userComplete = userCompleteService.login(account.getAccountEmail(), account.getAccountPassword());
        if (userComplete == null) {
            return "redirect:/login";
        } else {
            session = request.getSession();
            logger.info("Session id is "+session.getId());
            int userIDAccount = userComplete.getUserID();
            userComplete = userCompleteService.login(account.getAccountEmail(), account.getAccountPassword());
    
            session.setAttribute("userIDAccount",userIDAccount);
            session.setAttribute("userComplete",userComplete);
            
            model.addAttribute("user", userComplete);
            return "Home";
            
        }
    }
    
    @GetMapping("/homePage")
    public String homePage( Model model) {

            
                model.addAttribute("user", (UserComplete)session.getAttribute("userComplete"));
                return "Home";

        
    }
    
    // CONTACT LIST MANAGEMENT:
    @GetMapping("/contacts")
    public String contactView(Model model) {
        Iterable<ContactView> contactViewList = contactViewService.getContactViewList((int)session.getAttribute("userIDAccount"));
        model.addAttribute("contacts", contactViewList);
        return "Contacts";
    }
    
    @GetMapping("/deleteContact/{id}")
    public ModelAndView deleteContact(@PathVariable("id") final int id) {
        logger.info("delete contact with id  " + id + " in View Controller");
        contactService.deleteContact(id);
        return new ModelAndView("redirect:/contacts");
    }
    
  
    @GetMapping("/createContact")
    public String createContact(Model model) {
        ContactView contactView = new ContactView();
        List<ContactView> contactViewList = contactViewService.getContactViewList((int)session.getAttribute("userIDAccount"));
        List<String> contactViewMails = new ArrayList<String>();
        for (ContactView cv : contactViewList) {
            contactViewMails.add(cv.getEmail());
        }
        logger.info(contactViewMails);
        List<String> emailList = new ArrayList<String>();
        for (Account account : accountService.readAccountList()) {
            if (account.getUserID() != ((int)session.getAttribute("userIDAccount")) && !(contactViewMails.contains(account.getAccountEmail()))) {
                emailList.add(account.getAccountEmail());
            }
        }
        model.addAttribute("contact", contactView);
        model.addAttribute("emails", emailList);
        return "addContact";
    }
    
    @PostMapping("/saveContact")
    public ModelAndView saveContact(@ModelAttribute ContactView contact) {
        
        List<Account> accountList = accountService.readAccountList();
        
        int userIDContact = 0;
        for (Account account : accountList) {
            if (account.getAccountEmail().equals(contact.getEmail())) {
                userIDContact = account.getUserID();
            }
        }
        contactService.createContact((int)session.getAttribute("userIDAccount"), userIDContact);
        return new ModelAndView("redirect:/contacts");
    }
    
   
}
