package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.model.UserComplete;
import com.paymybuddy.PayMyBuddy.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
   
}
