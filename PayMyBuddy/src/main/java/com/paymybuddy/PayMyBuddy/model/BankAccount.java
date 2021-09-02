package com.paymybuddy.PayMyBuddy.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BankAccount {
    private int bankAccountID;
    private int bankAccountNumber;
    private double bankAccountAmount;
    private String bankAccountCurrency;
    private int userID;
}
