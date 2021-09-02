package com.paymybuddy.PayMyBuddy.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Connection {
    private int connectionID;
    private int userIDAccount;
    private int userIDContact;
}
