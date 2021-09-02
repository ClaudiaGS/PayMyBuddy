package com.paymybuddy.PayMyBuddy.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Transaction {
    private int transactionID;
    private String transactionDescription;
    private double transactionDebitedAmount;
    private double transactionFeeAmount;
    private double transactionReceivedAmount;
    private int userIDSender;
    private int userIDReceiver;
}
