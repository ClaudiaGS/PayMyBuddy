package com.paymybuddy.PayMyBuddy.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Operation {
    private String operationName;
    private double amountForOperation;
    private double amount;
}
