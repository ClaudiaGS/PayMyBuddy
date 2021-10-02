package com.paymybuddy.PayMyBuddy.controller;

import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    
    @PostMapping("/createTransaction")
    public boolean createTransaction(Transaction transaction){
        return transactionService.createTransaction(transaction);
    }
    @GetMapping("/readTransactionInfo")
    public Transaction readTransaction(@RequestParam int transactionID){
        return transactionService.readTransaction(transactionID);
    }
    @GetMapping("/readTransactionListInfo")
    public List<Transaction> readTransactionList(){
        return transactionService.readTransactionList();
    }
    @GetMapping("/readUsersTransactionListInfo")
    public List<Transaction> readUsersTransactionList(@RequestParam int userIDSender) {
        return transactionService.readUsersTransactionList(userIDSender);
        
    }

    
}
