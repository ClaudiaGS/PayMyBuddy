package com.paymybuddy.PayMyBuddy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.controller.AccountController;
import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.service.interfaces.IAccountService;
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

@SpringBootTest(classes = AccountController.class)
@EnableWebMvc
@AutoConfigureMockMvc
public class AccountControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    IAccountService accountService;
    @MockBean
    public IDataBase dataBaseTest;
    
    List<Account> accountList = new ArrayList<>();
    Account account1 = new Account();
    Account account2 = new Account();
    
    @BeforeEach()
    public void config() {
        account1.setAccountID(1);
        account1.setAccountEmail("email1");
        account1.setAccountPassword("pass1");
        account1.setUserID(1);
        account2.setAccountID(2);
        account2.setAccountEmail("email2");
        account2.setAccountPassword("pass2");
        account2.setUserID(2);
        accountList.add(account1);
        accountList.add(account2);
    }
    
    
    @Test
    public void readAccountListTest() {
        try {
            when(accountService.readAccountList()).thenReturn(accountList);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/readAccountListInfo")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void readAccountTest() {
        try {
            when(accountService.readAccount(1)).thenReturn(account1);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/readAccountInfo")
                    .param("accountID", "1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void createAccountTest() {
        Account newAccount = new Account();
        newAccount.setAccountID(3);
        newAccount.setAccountEmail("newEmail");
        newAccount.setAccountPassword("newPass");
        newAccount.setUserID(3);
        
        try (Connection connection = dataBaseTest.getConnection()) {
            
            when(accountService.createAccount(connection, newAccount)).thenReturn(true);
            this.mvc.perform(MockMvcRequestBuilders
                    .post("/createAccount")
                    .param("userID", "3")
                    .param("email", "newEmail")
                    .param("password", "newPass")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void readUsersAccountTest() {
        
        try {
            when(accountService.readUsersAccount(1)).thenReturn(account1);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/readUsersAccountInfo")
                    .param("userID", "1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void updateAccountTest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("accountPassword", "passUpdated");
        
        try {
            when(accountService.updateAccount(3, params)).thenReturn(true);
            this.mvc.perform(MockMvcRequestBuilders
                    .put("/updateAccount")
                    .param("accountID", "3")
                    .content(asJsonString(params))
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void authenticate() {
        Account account = new Account();
        account.setAccountEmail("email1");
        account.setAccountPassword("pass1");
        
        try {
            when(accountService.authenticate(account)).thenReturn(account1);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/authenticate")
                    .param("email", "email1")
                    .param("password", "pass")
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



