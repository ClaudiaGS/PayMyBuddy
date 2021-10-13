package com.paymybuddy.PayMyBuddy.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Contact {
    private int contactID;
    private int userIDAccount;
    private int userIDContact;
}
