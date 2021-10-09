package com.paymybuddy.PayMyBuddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.integration.config.DataBaseTestConfig;
import com.paymybuddy.PayMyBuddy.model.Contact;
import com.paymybuddy.PayMyBuddy.service.interfaces.IContactForTransaction;
import com.paymybuddy.PayMyBuddy.service.interfaces.IContactService;
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
public class ContactForTransactionServiceIT {
    
    @Autowired
    IContactForTransaction contactForTransactionService;
    @Autowired
    IContactService contactService;
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
    public void getContactForTransactionList(){
        assertThat(asJsonString(contactForTransactionService.getContactForTransactionList(1))).isEqualTo("[]");
    }
    @Test
    public void  readContactForTransactionIT()
    {
        Contact contact=new Contact();
        contact.setUserIDAccount(1);
        contact.setUserIDContact(2);
        contactService.createContact(contact);
        
        assertThat(asJsonString(contactForTransactionService.readContactForTransaction(1))).isEqualTo("{\"contactID\":1,\"firstName\":\"firstname2\",\"lastName\":\"lastname2\",\"email\":\"account2@email\"}");
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
