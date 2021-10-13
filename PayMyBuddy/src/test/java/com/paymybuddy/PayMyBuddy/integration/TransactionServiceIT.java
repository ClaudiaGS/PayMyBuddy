package com.paymybuddy.PayMyBuddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.integration.config.DataBaseTestConfig;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.service.interfaces.ITransactionService;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(DataBaseTestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class TransactionServiceIT {
    
    @Autowired
    ITransactionService transactionService;
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
    @Order(1)
    public void createTransactionIT() {
        Transaction transaction = new Transaction();
        transaction.setTransactionDescription("cake");
        transaction.setTransactionReceivedAmount(1.5);
        transaction.setUserIDSender(2);
        transaction.setUserIDReceiver(1);
        assertThat(transactionService.createTransaction(transaction)).isEqualTo(true);
    }
    
    @Test
    @Order(2)
    public void readTransactionListIT() {
        assertThat(asJsonString(transactionService.readTransactionList())).isEqualTo("[{\"transactionID\":1,\"transactionDescription\":\"cake\",\"transactionDebitedAmount\":1.51,\"transactionFeeAmount\":0.01,\"transactionReceivedAmount\":1.5,\"userIDSender\":2,\"userIDReceiver\":1}]");
    }
    
    @Test
    @Order(3)
    public void readUsersTransactionListIT() {
        assertThat(asJsonString(transactionService.readUsersTransactionList(2))).isEqualTo("[{\"transactionID\":1,\"transactionDescription\":\"cake\",\"transactionDebitedAmount\":1.51,\"transactionFeeAmount\":0.01,\"transactionReceivedAmount\":1.5,\"userIDSender\":2,\"userIDReceiver\":1}]");
    }
    
    
    @Test
    @Order(4)
    public void readTransactionIT() {
        assertThat(asJsonString(transactionService.readTransaction(1))).isEqualTo("{\"transactionID\":1,\"transactionDescription\":\"cake\",\"transactionDebitedAmount\":1.51,\"transactionFeeAmount\":0.01,\"transactionReceivedAmount\":1.5,\"userIDSender\":2,\"userIDReceiver\":1}");
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
