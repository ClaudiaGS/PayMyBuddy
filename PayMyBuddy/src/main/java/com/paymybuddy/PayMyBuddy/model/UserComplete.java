package com.paymybuddy.PayMyBuddy.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Data
@Component
public class UserComplete {
    private int userID;
    private String userFirstName;
    private String userLastName;
    private Date userBirthdate;

    
    private BankAccount usersBankAccount;
    private List<Contact> contactList;
    private List<Transaction> transactionList;
    private Account account;
    
}

