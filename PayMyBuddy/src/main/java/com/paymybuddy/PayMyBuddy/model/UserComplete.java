package com.paymybuddy.PayMyBuddy.model;

import java.awt.*;
import java.util.Date;
import java.util.List;

public class UserComplete {
    private int userID;
    private String userFirstName;
    private String userLastName;
    private Date userBirthdate;
    private Image userProfilePicture;
    
    private BankAccount usersBankAccount;
    private List<Contact> contactList;
    private List<Transaction> transactionList;
    private Account account;
    
}

