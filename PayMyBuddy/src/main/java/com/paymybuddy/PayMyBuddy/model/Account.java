package com.paymybuddy.PayMyBuddy.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Account {
    private int accountID;
    private String accountEmail;
    private String accountPassword;
    private int userID;
}
