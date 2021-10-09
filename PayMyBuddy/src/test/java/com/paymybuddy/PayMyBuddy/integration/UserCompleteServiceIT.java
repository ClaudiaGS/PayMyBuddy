package com.paymybuddy.PayMyBuddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.integration.config.DataBaseTestConfig;
import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserCompleteService;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(DataBaseTestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class UserCompleteServiceIT {
    
    @Autowired
    IUserCompleteService userCompleteService;
    @Autowired
    public IDataBase dataBaseTest;
    
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
    public void loginIT(){
        Account account=new Account();
        account.setAccountEmail("account1@email");
        account.setAccountPassword("password1");
        assertThat(asJsonString(userCompleteService.login(account))).isEqualTo("{\"userID\":1,\"userFirstName\":\"firstname1\",\"userLastName\":\"lastname1\",\"usersBankAccount\":{\"bankAccountID\":1,\"bankAccountAmount\":1000.0,\"bankAccountCurrency\":\"EURO\",\"userID\":1},\"contactList\":[],\"transactionList\":[],\"account\":{\"accountID\":1,\"accountEmail\":\"account1@email\",\"accountPassword\":\"e38ad214943daad1d64c102faec29de4afe9da3d\",\"userID\":1}}");
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void readUserCompleteList(){
        assertThat(asJsonString(userCompleteService.readUserCompleteList())).isEqualTo("[{\"userID\":1,\"userFirstName\":\"firstname1\",\"userLastName\":\"lastname1\",\"usersBankAccount\":{\"bankAccountID\":1,\"bankAccountAmount\":1000.0,\"bankAccountCurrency\":\"EURO\",\"userID\":1},\"contactList\":[],\"transactionList\":[],\"account\":{\"accountID\":1,\"accountEmail\":\"account1@email\",\"accountPassword\":\"e38ad214943daad1d64c102faec29de4afe9da3d\",\"userID\":1}},{\"userID\":2,\"userFirstName\":\"firstname2\",\"userLastName\":\"lastname2\",\"usersBankAccount\":{\"bankAccountID\":2,\"bankAccountAmount\":2000.0,\"bankAccountCurrency\":\"EURO\",\"userID\":2},\"contactList\":[],\"transactionList\":[],\"account\":{\"accountID\":2,\"accountEmail\":\"account2@email\",\"accountPassword\":\"2aa60a8ff7fcd473d321e0146afd9e26df395147\",\"userID\":2}},{\"userID\":3,\"userFirstName\":\"firstname3\",\"userLastName\":\"lastname3\",\"usersBankAccount\":{\"bankAccountID\":3,\"bankAccountAmount\":3000.0,\"bankAccountCurrency\":\"EURO\",\"userID\":3},\"contactList\":[],\"transactionList\":[],\"account\":{\"accountID\":3,\"accountEmail\":\"account3@email\",\"accountPassword\":\"1119cfd37ee247357e034a08d844eea25f6fd20f\",\"userID\":3}}]");
    }
}
//userComplete.setUserID(account.getUserID());
//        userComplete.setUserFirstName(user.getUserFirstName());
//        userComplete.setUserLastName(user.getUserLastName());
//        userComplete.setUsersBankAccount(bankAccount);
//        userComplete.setContactList(contactsList);
//        userComplete.setTransactionList(transactionList);
//        userComplete.setAccount(account);