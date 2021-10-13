package com.paymybuddy.PayMyBuddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.integration.config.DataBaseTestConfig;
import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.service.interfaces.IAccountService;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserService;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(DataBaseTestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class AccountServiceIT {
    
    @Autowired
    IAccountService accountService;
    @Autowired
    public IDataBase dataBaseTest;
    @Autowired
    IUserService userService;
    @BeforeAll
    public void config() {
        Connection con = null;
        ScriptRunner sr = null;
        Reader reader = null;
        try {
            con = dataBaseTest.getConnection();
            sr = new ScriptRunner(con);
            reader = new BufferedReader(new FileReader("F:\\OPENCLASSROOMS\\PROJET 6\\PayMyBuddyGit\\PayMyBuddy\\src\\test\\java\\com\\paymybuddy\\PayMyBuddy\\integration\\config\\resources\\DataTest.sql"));
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sr.runScript(reader);
        
    }
    
    @Test
    @Order(1)
    public void createAccountIT() {
        Connection con = null;
        try {
            con = dataBaseTest.getConnection();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        User user4=new User();
        user4.setUserFirstName("firstname4");
        user4.setUserLastName("lastname4");
        userService.createUser(user4);
        
        Account account = new Account();
        account.setAccountEmail("account4@email");
        account.setAccountPassword("password4");
        account.setUserID(4);
        assertThat(accountService.createAccount(con, account)).isEqualTo(true);
    }
    
    @Test
    @Order(2)
    public void readAccountListIT() {
        assertThat(asJsonString(accountService.readAccountList())).isEqualTo("[{\"accountID\":1,\"accountEmail\":\"account1@email\",\"accountPassword\":\"e38ad214943daad1d64c102faec29de4afe9da3d\",\"userID\":1},{\"accountID\":2,\"accountEmail\":\"account2@email\",\"accountPassword\":\"2aa60a8ff7fcd473d321e0146afd9e26df395147\",\"userID\":2},{\"accountID\":3,\"accountEmail\":\"account3@email\",\"accountPassword\":\"1119cfd37ee247357e034a08d844eea25f6fd20f\",\"userID\":3},{\"accountID\":4,\"accountEmail\":\"account4@email\",\"accountPassword\":\"a1d7584daaca4738d499ad7082886b01117275d8\",\"userID\":4}]");
    }
    
    @Test
    @Order(3)
    public void readUsersAccountIT() {
        assertThat(asJsonString(accountService.readUsersAccount(1))).isEqualTo("{\"accountID\":1,\"accountEmail\":\"account1@email\",\"accountPassword\":\"e38ad214943daad1d64c102faec29de4afe9da3d\",\"userID\":1}");
    }
    
    @Test
    @Order(4)
    public void readAccountIT() {
        assertThat(asJsonString(accountService.readAccount(1))).isEqualTo("{\"accountID\":1,\"accountEmail\":\"account1@email\",\"accountPassword\":\"e38ad214943daad1d64c102faec29de4afe9da3d\",\"userID\":1}");
    }
    
    @Test
    @Order(5)
    public void updateAccountIT() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("accountPassword", "newPassword");
        assertThat((accountService.updateAccount(1, params))).isEqualTo(true);
    }
    
    @Test
    @Order(6)
    public void authenticateIT() {
        Account account = new Account();
        account.setAccountEmail("account2@email");
        account.setAccountPassword("password2");
        assertThat(asJsonString(accountService.authenticate(account))).isEqualTo("{\"accountID\":2,\"accountEmail\":\"account2@email\",\"accountPassword\":\"2aa60a8ff7fcd473d321e0146afd9e26df395147\",\"userID\":2}");
    }
    
    @Test
    @Order(7)
    public void alreadyExistIT() {
        assertThat((accountService.alreadyExist("account2@email"))).isEqualTo(true);
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}