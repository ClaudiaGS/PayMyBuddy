package com.paymybuddy.PayMyBuddy.model;

import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class User {
    private int userID;
    private String userFirstName;
    private String userLastName;
}
