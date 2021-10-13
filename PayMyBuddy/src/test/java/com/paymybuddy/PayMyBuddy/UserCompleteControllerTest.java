package com.paymybuddy.PayMyBuddy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.controller.UserCompleteController;
import com.paymybuddy.PayMyBuddy.model.*;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserCompleteService;
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

@SpringBootTest(classes = UserCompleteController.class)
@EnableWebMvc
@AutoConfigureMockMvc
public class UserCompleteControllerTest {
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    IUserCompleteService userCompleteService;
    
    UserComplete userComplete1 = new UserComplete();
    Account account = new Account();
    BankAccount bankAccount = new BankAccount();
    List<Transaction> transactions = new ArrayList<>();
    Transaction transaction = new Transaction();
    List<Contact> contacts = new ArrayList<>();
    Contact contact = new Contact();
    
    
    @BeforeEach
    public void config() {
        account.setAccountEmail("email1");
        account.setAccountPassword("password1");
        transactions.add(transaction);
        contacts.add(contact);
        userComplete1.setUserID(1);
        userComplete1.setAccount(account);
        userComplete1.setUsersBankAccount(bankAccount);
        userComplete1.setUserLastName("last");
        userComplete1.setUserFirstName("first");
        userComplete1.setTransactionList(transactions);
        userComplete1.setContactList(contacts);
    }
    
    @Test
    public void loginTest() {
        
        try {
            when(userCompleteService.login(account)).thenReturn(userComplete1);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/loginUserComplete")
                    .content(asJsonString(account))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void readUserCompleteListTest() {
        List<UserComplete> userCompleteList = new ArrayList<>();
        userCompleteList.add(userComplete1);
        try {
            when(userCompleteService.readUserCompleteList()).thenReturn(userCompleteList);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/userCompleteList")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void readUserCompleteTest() {
        
        try {
            when(userCompleteService.readUserComplete(1)).thenReturn(userComplete1);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/userComplete")
                    .param("userID", "1")
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
