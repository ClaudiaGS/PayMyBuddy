package com.paymybuddy.PayMyBuddy.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;


@Data
@Component
public class User {
    private int userID;
    private String userFirstName;
    private String userLastName;
    private Date userBirthdate;

}
