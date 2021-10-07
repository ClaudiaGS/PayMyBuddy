package com.paymybuddy.PayMyBuddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.integration.config.DataBaseTestConfig;
import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.service.interfaces.IAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(DataBaseTestConfig.class)
@SpringBootTest
public class AccountServiceIT {
    
    @Autowired
    IAccountService accountService;
    @Autowired
    IDataBase dataBaseTest;
    
    Connection testConnection;
    
    @BeforeEach
    public void config() {
        try {
            testConnection = dataBaseTest.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    @Test
    public void createAccountIT() {
        Account account=new Account();
        account.setAccountEmail("account4@email");
        account.setAccountPassword("password4");
        assertThat(accountService.createAccount(testConnection, account)).isEqualTo(true);
    }
    @Test
    public void readAccountListIT(){
        assertThat(asJsonString(accountService.readAccountList())).isEqualTo("\"[{\\\"accountID\\\":1,\\\"accountEmail\\\":\\\"account1@email\\\",\\\"accountPassword\\\":\\\"e38ad214943daad1d64c102faec29de4afe9da3d\\\",\\\"userID\\\":1},{\\\"accountID\\\":2,\\\"accountEmail\\\":\\\"account2@email\\\",\\\"accountPassword\\\":\\\"2aa60a8ff7fcd473d321e0146afd9e26df395147\\\",\\\"userID\\\":2},{\\\"accountID\\\":3,\\\"accountEmail\\\":\\\"account3@email\\\",\\\"accountPassword\\\":\\\"1119cfd37ee247357e034a08d844eea25f6fd20f\\\",\\\"userID\\\":3},{\\\"accountID\\\":4,\\\"accountEmail\\\":\\\"account4@email\\\",\\\"accountPassword\\\":\\\"a1d7584daaca4738d499ad7082886b01117275d8\\\",\\\"userID\\\":4}]\"");
    }
    @Test
    public void readUsersAccountIT(){
        assertThat(asJsonString(accountService.readUsersAccount(1))).isEqualTo("{\"accountID\":1,\"accountEmail\":\"account1@email\",\"accountPassword\":\"e38ad214943daad1d64c102faec29de4afe9da3d\",\"userID\":1}");
    }
    
    @Test
    public void readAccountIT(){
        assertThat(asJsonString(accountService.readAccount(1))).isEqualTo("{\"accountID\":1,\"accountEmail\":\"account1@email\",\"accountPassword\":\"e38ad214943daad1d64c102faec29de4afe9da3d\",\"userID\":1}");
    }
    @Test
    public void updateAccountIT(){
        HashMap<String,Object>params=new HashMap<>();
        params.put("accountPassword", "newPassword");
        assertThat((accountService.updateAccount(1,params))).isEqualTo(true);
    }
    @Test
    public void authenticateIT(){
        Account account=new Account();
        account.setAccountEmail("account2@email");
        account.setAccountPassword("password2");
        assertThat(asJsonString(accountService.authenticate(account))).isEqualTo("{\"accountID\":2,\"accountEmail\":\"account2@email\",\"accountPassword\":\"2aa60a8ff7fcd473d321e0146afd9e26df395147\",\"userID\":2}");
    }
    @Test
    public  void alreadyExistIT(){
        assertThat(asJsonString(accountService.alreadyExist("account2@email"))).isEqualTo(true);
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}