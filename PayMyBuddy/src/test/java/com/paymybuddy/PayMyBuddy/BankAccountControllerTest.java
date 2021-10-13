package com.paymybuddy.PayMyBuddy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.controller.BankAccountController;
import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService;
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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BankAccountController.class)
@EnableWebMvc
@AutoConfigureMockMvc
public class BankAccountControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    IBankAccountService bankAccountService;
    @MockBean
    public IDataBase dataBaseTest;
    
    List<BankAccount> bankAccountList = new ArrayList<>();
    BankAccount bankAccount1 = new BankAccount();
    BankAccount bankAccount2 = new BankAccount();
    
    @BeforeEach()
    public void config() {
        bankAccount1.setBankAccountID(1);
        bankAccount1.setBankAccountAmount(100);
        bankAccount1.setBankAccountCurrency("euro");
        bankAccount1.setUserID(1);
        bankAccount2.setBankAccountID(2);
        bankAccount2.setBankAccountAmount(200);
        bankAccount2.setBankAccountCurrency("euro");
        bankAccount2.setUserID(2);
        
        bankAccountList.add(bankAccount1);
        bankAccountList.add(bankAccount2);
    }
    
    @Test
    public void readBankAccountListTest() {
        try {
            when(bankAccountService.readBankAccountList()).thenReturn(bankAccountList);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/readBankAccountList")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void readBankAccountTest() {
        try {
            when(bankAccountService.readBankAccount(1)).thenReturn(bankAccount1);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/readBankAccount")
                    .param("bankAccountID", "1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void createBankAccountTest() {
        BankAccount newBankAccount = new BankAccount();
        newBankAccount.setBankAccountID(3);
        newBankAccount.setBankAccountAmount(300);
        newBankAccount.setBankAccountCurrency("euro");
        newBankAccount.setUserID(3);
        try (Connection connection = dataBaseTest.getConnection()) {
            
            when(bankAccountService.createBankAccount(connection, newBankAccount)).thenReturn(true);
            this.mvc.perform(MockMvcRequestBuilders
                    .post("/createBankAccount")
                    .param("bankAccountAmount", "300")
                    .param("bankAccountCurrency", "euro")
                    .param("userID", "3")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void readUsersBankAccountTest() {
        
        try {
            when(bankAccountService.readUsersBankAccount(1)).thenReturn(bankAccount1);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/readUsersBankAccount")
                    .param("userID", "1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void updateBankAccountTest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("bankAccountAmount", "500");
        
        try(Connection connection=dataBaseTest.getConnection()) {
            when(bankAccountService.updateBankAccount(connection,3,params)).thenReturn(true);
            this.mvc.perform(MockMvcRequestBuilders
                    .put("/updateBankAccount")
                    .param("bankAccountID", "3")
                    .content(asJsonString(params))
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            e.printStackTrace();
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




