package com.paymybuddy.PayMyBuddy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.controller.TransactionController;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.service.interfaces.ITransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TransactionController.class)
@EnableWebMvc
@AutoConfigureMockMvc
public class TransactionControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    ITransactionService transactionService;
    @MockBean
    public IDataBase dataBaseTest;
    
    List<Transaction> transactionList = new ArrayList<>();
    Transaction transaction1 = new Transaction();
    Transaction transaction2 = new Transaction();
    
    @BeforeEach
    public void config() {
        transaction1.setTransactionID(1);
        transaction1.setTransactionDescription("bus");
        transaction1.setTransactionReceivedAmount(5);
        transaction1.setUserIDSender(1);
        transaction1.setUserIDReceiver(2);
        transaction2.setTransactionID(2);
        transaction2.setTransactionDescription("cake");
        transaction2.setTransactionReceivedAmount(10);
        transaction2.setUserIDSender(2);
        transaction2.setUserIDReceiver(1);
        transactionList.add(transaction1);
        transactionList.add(transaction2);
    }
    
    @Test
    public void readTransactionListTest() {
        try {
            when(transactionService.readTransactionList()).thenReturn(transactionList);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/readTransactionListInfo")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void readTransactionTest() {
        try {
            when(transactionService.readTransaction(1)).thenReturn(transaction1);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/readTransactionInfo")
                    .param("transactionID", "1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void createTransactionTest() {
        Transaction transaction = new Transaction();
        transaction.setTransactionDescription("book");
        transaction.setTransactionReceivedAmount(10);
        transaction.setUserIDSender(1);
        transaction.setUserIDReceiver(2);
        try {
            when(transactionService.createTransaction(transaction)).thenReturn(true);
            this.mvc.perform(MockMvcRequestBuilders
                    .post("/createTransaction")
                    .content(asJsonString(transaction))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void readUsersTransactionListTest() {
        List<Transaction> user1Transactions = new ArrayList<>();
        user1Transactions.add(transaction1);
        
        try {
            when(transactionService.readUsersTransactionList(1)).thenReturn(user1Transactions);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/readUsersTransactionListInfo")
                    .param("userIDSender", "1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
}
