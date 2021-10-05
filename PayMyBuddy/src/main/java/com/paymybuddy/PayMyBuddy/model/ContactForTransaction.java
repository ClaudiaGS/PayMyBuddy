package com.paymybuddy.PayMyBuddy.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ContactForTransaction {
    private int contactID;
    private String firstName;
    private String lastName;
    private String email;
}
