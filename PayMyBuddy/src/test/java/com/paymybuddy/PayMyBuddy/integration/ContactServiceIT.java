package com.paymybuddy.PayMyBuddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.integration.config.DataBaseTestConfig;
import com.paymybuddy.PayMyBuddy.model.Contact;
import com.paymybuddy.PayMyBuddy.service.interfaces.IContactService;
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
public class ContactServiceIT {
    
    @Autowired
    IContactService contactService;
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
    public void createContact() {
        Contact contact=new Contact();
        contact.setUserIDContact(2);
        contact.setUserIDAccount(1);
        assertThat(contactService.createContact(contact)).isEqualTo(true);
    }
    @Test
    public void readContactListIT() {
        assertThat(asJsonString(contactService.readContactList())).isEqualTo("[{\"contactID\":1,\"userIDAccount\":1,\"userIDContact\":2}]");
    }
    @Test
    public void readUsersContactList(){
        assertThat(asJsonString(contactService.readUsersContactList(1))).isEqualTo("[{\"contactID\":1,\"userIDAccount\":1,\"userIDContact\":2}]");
    }
    
    @Test
    public void readContactIT(){
        assertThat(asJsonString(contactService.readContact(1))).isEqualTo("[{\"contactID\":1,\"userIDAccount\":1,\"userIDContact\":2}]");
    }
    
    @Test
    public void deleteContactIT(){
        HashMap<String,Object> params=new HashMap<>();
        params.put("userIDContact", 3);
        assertThat(contactService.deleteContact(1)).isEqualTo(true);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
