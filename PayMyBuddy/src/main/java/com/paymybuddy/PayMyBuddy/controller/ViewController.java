package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.config.DataBase;
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
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ViewController {
    @Autowired
    DataBase dataSource;
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
    @Autowired
    UserService userService;
    @Autowired
    BankAccountService bankAccountService;
    
    
    private static final Logger logger = LogManager.getLogger("ViewController");
    
    //LOGIN
    @GetMapping("/login")
    public String connexion(Model model, Account account) {
        
        model.addAttribute("account", account);
        
        return "Login";
    }
    
    @PostMapping("/home")
    public String home(@ModelAttribute Account account, Model model, HttpServletRequest request) {
        TransactionView transaction = new TransactionView();
        
        UserComplete userComplete = userCompleteService.login(account);
        if (userComplete == null) {
            return "redirect:/login";
        } else {
            HttpSession session = request.getSession();
            logger.info("Session id is " + session.getId());
            int userIDAccount = userComplete.getUserID();
            userComplete = userCompleteService.login(account);
            
            session.setAttribute("userIDAccount", userIDAccount);
            session.setAttribute("userComplete", userComplete);
            List<TransactionView> transactionViewList = transactionViewService.getTransactionViewList(userIDAccount);
            if (transactionViewList.size() > 0) {
                transaction = transactionViewList.get(transactionViewList.size() - 1);
            } else {
                transaction = null;
            }
            Double amount = bankAccountService.readUsersBankAccount(userIDAccount).getBankAccountAmount();
            model.addAttribute("amount", amount);
            model.addAttribute("transaction", transaction);
            model.addAttribute("user", userComplete);
            return "Home";
            
        }
    }
    
    @GetMapping("/homePage")
    public String homePage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        TransactionView transaction = new TransactionView();
        List<TransactionView> transactionViewList = transactionViewService.getTransactionViewList((int) session.getAttribute("userIDAccount"));
        if (transactionViewList.size() > 0) {
            transaction = transactionViewList.get(transactionViewList.size() - 1);
        } else {
            transaction = null;
        }
        Double amount = bankAccountService.readUsersBankAccount((int) session.getAttribute("userIDAccount")).getBankAccountAmount();
        model.addAttribute("amount", amount);
        model.addAttribute("transaction", transaction);
        model.addAttribute("user", (UserComplete) session.getAttribute("userComplete"));
        return "Home";
        
        
    }
    
    //REGISTRATION
    @GetMapping("/registration")
    public String registration(Model model) {
        RegisterInfoView registerInfoView = new RegisterInfoView();
        model.addAttribute("registerInfoView", registerInfoView);
        return "Registration";
    }
    
    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute RegisterInfoView registerInfoView, Model model, HttpServletRequest request) {
        
        String viewToReturn = "Home";
        Account account = new Account();
        account.setAccountEmail(registerInfoView.getEmail());
        account.setAccountPassword(registerInfoView.getPassword());
        
        
        User user = new User();
        user.setUserFirstName(registerInfoView.getFirstName());
        user.setUserLastName(registerInfoView.getLastName());
        if (registerInfoView.getBirthdate() == null) {
            user.setUserBirthdate(null);
        } else {
            user.setUserBirthdate(registerInfoView.getBirthdate());
        }
        if (registerInfoView.getPassword().equals(registerInfoView.getRePassword())) {
            if (userService.registration(account, user)) {
                
                logger.info("Registration succeeded");
                
                UserComplete userComplete = userCompleteService.login(account);
                HttpSession session = request.getSession();
                int userIDAccount = userComplete.getUserID();
                session.setAttribute("userIDAccount", userIDAccount);
                session.setAttribute("userComplete", userComplete);
                List<TransactionView> transactionViewList = transactionViewService.getTransactionViewList(userIDAccount);
                TransactionView transaction = new TransactionView();
                
                // get last transaction for the Home view
                if (transactionViewList.size() > 0) {
                    transaction = transactionViewList.get(transactionViewList.size() - 1);
                } else {
                    transaction = null;
                }
                
                Double amount = bankAccountService.readUsersBankAccount(userIDAccount).getBankAccountAmount();
                model.addAttribute("amount", amount);
                model.addAttribute("transaction", transaction);
                model.addAttribute("user", userComplete);
                model.addAttribute("account", account);
                
                viewToReturn = "Home";
            } else {
                logger.error("Missing info to register");
                viewToReturn = "Registration";
            }
        } else {
            viewToReturn = "Registration";
            logger.error("Password not the same");
        }
        return viewToReturn;
        
    }
    
    
    // CONTACT LIST MANAGEMENT:
    @GetMapping("/contacts")
    public String contactView(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Iterable<ContactView> contactViewList = contactViewService.getContactViewList((int) session.getAttribute("userIDAccount"));
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
    public String createContact(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ContactView contactView = new ContactView();
        List<ContactView> contactViewList = contactViewService.getContactViewList((int) session.getAttribute("userIDAccount"));
        List<String> contactViewMails = new ArrayList<String>();
        for (ContactView cv : contactViewList) {
            contactViewMails.add(cv.getEmail());
        }
        List<String> emailList = new ArrayList<String>();
        for (Account account : accountService.readAccountList()) {
            if (account.getUserID() != ((int) session.getAttribute("userIDAccount")) && !(contactViewMails.contains(account.getAccountEmail()))) {
                emailList.add(account.getAccountEmail());
            }
        }
        model.addAttribute("contact", contactView);
        model.addAttribute("emails", emailList);
        return "addContact";
    }
    
    @PostMapping("/saveContact")
    public ModelAndView saveContact(@ModelAttribute ContactView contact, HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        List<Account> accountList = accountService.readAccountList();
        
        int userIDContact = 0;
        for (Account account : accountList) {
            if (account.getAccountEmail().equals(contact.getEmail())) {
                userIDContact = account.getUserID();
            }
        }
        Contact contactBase = new Contact();
        contactBase.setUserIDAccount((int) session.getAttribute("userIDAccount"));
        contactBase.setUserIDContact(userIDContact);
        contactService.createContact(contactBase);
        return new ModelAndView("redirect:/contacts");
    }
    
    
    // TRANSACTIONS MANAGEMENT:
    @GetMapping("/transactions")
    public String transactionView(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Iterable<TransactionView> transactionViewList = transactionViewService.getTransactionViewList((int) session.getAttribute("userIDAccount"));
        model.addAttribute("transactions", transactionViewList);
        return "Transactions";
    }
    
    @GetMapping("/createTransaction")
    public String createTransaction(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Iterable<ContactView> contactViewList = contactViewService.getContactViewList((int) session.getAttribute("userIDAccount"));
        model.addAttribute("contacts", contactViewList);
        TransactionView transactionView = new TransactionView();
        model.addAttribute("transaction", transactionView);
//        UserComplete userComplete = (UserComplete) session.getAttribute("userComplete");
//        model.addAttribute("bankAccountAmount", userComplete.getUsersBankAccount().getBankAccountAmount());
//        model.addAttribute("amount", transactionView.getAmount());
        logger.info("HERE been in /createTransaction");
        return "addTransaction";
    }
    
    @PostMapping("/saveTransaction")
    public ModelAndView saveTransaction(@ModelAttribute TransactionView transaction, String description, Double
            amount, HttpServletRequest request) {
        HttpSession session = request.getSession();
        for (ContactView cv : contactViewService.getContactViewList((int) session.getAttribute("userIDAccount"))) {
            if (cv.getContactID() == transaction.getContactID()) {
                transaction.setFirstName(cv.getFirstName());
                transaction.setLastName(cv.getLastName());
            }
        }
        transaction.setDescription(description);
        transaction.setAmount(amount);
        
        
        int userIDReceiver = 0;
        for (UserComplete user : userCompleteService.readUserCompleteList()) {
            if (user.getUserFirstName().equals(transaction.getFirstName()) && user.getUserLastName().equals(transaction.getLastName())) {
                userIDReceiver = user.getUserID();
            }
        }
        
        Transaction transactionToCreate = new Transaction();
        transactionToCreate.setTransactionDescription(transaction.getDescription());
        transactionToCreate.setTransactionReceivedAmount(amount);
        transactionToCreate.setUserIDSender((int) session.getAttribute("userIDAccount"));
        transactionToCreate.setUserIDReceiver(userIDReceiver);
        
        UserComplete userComplete = (UserComplete) session.getAttribute("userComplete");
        if (userComplete.getUsersBankAccount().getBankAccountAmount() >= amount) {
            transactionService.createTransaction(transactionToCreate);
        } else {
            logger.error("Available money: " + userComplete.getUsersBankAccount().getBankAccountAmount() + " Cannot send!");
        }
        return new ModelAndView("redirect:/transactions");
    }
    
    
    //ACCOUNT AMOUNT OPERATIONS
    
    @GetMapping("/amountOperation")
    public String operatePersonalAmount(Model model, Operation operation, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Double amount = bankAccountService.readUsersBankAccount((int) session.getAttribute("userIDAccount")).getBankAccountAmount();
        
        operation.setAmount(amount);
        logger.info("amount first " + operation.getAmount());
        model.addAttribute("operation", operation);
        
        return "Operation";
    }
    
    @PostMapping("/updatePersonalAmount")
    public ModelAndView updateAmountPersonalAccount(@ModelAttribute Operation operation, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Connection connection = null;
        try {
            
            connection = this.dataSource.getConnection();
            
            
            UserComplete userComplete = (UserComplete) session.getAttribute("userComplete");
            operation.setAmount(userComplete.getUsersBankAccount().getBankAccountAmount());
            
            
            logger.info("amount is " + operation.getAmount() + " operation name " + operation.getOperationName() + " operation amount " + operation.getAmountForOperation());
            
            BankAccount bankAccount = bankAccountService.readUsersBankAccount((int) session.getAttribute("userIDAccount"));
            HashMap<String, Object> params = new HashMap<>();
            params.put("bankAccountAmount", bankAccountService.updateAmountPersonalAccount(operation.getAmount(), operation.getAmountForOperation(), operation.getOperationName()));
            bankAccountService.updateBankAccount(connection, bankAccount.getBankAccountID(), params);
        } catch (Exception e) {
            e.getMessage();
        }
        return new ModelAndView("redirect:/homePage");
    }
}
