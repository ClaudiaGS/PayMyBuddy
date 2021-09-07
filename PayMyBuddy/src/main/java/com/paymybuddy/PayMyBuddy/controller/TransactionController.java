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
    public Transaction createTransaction(@RequestBody Transaction newTransaction){
        return transactionService.createTransaction(newTransaction);
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
    
    @DeleteMapping("deleteTransaction")
    public boolean deleteTransaction(@RequestParam int transactionID){
        return transactionService.deleteTransaction(transactionID);
    }
    
}