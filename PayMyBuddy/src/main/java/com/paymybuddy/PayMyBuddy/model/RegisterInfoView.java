package com.paymybuddy.PayMyBuddy.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RegisterInfoView {
    private String email;
    private String password;
    private String rePassword;
    private String firstName;
    private String lastName;
}
