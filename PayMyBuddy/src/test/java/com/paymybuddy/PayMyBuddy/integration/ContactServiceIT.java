package com.paymybuddy.PayMyBuddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.integration.config.DataBaseTestConfig;
import com.paymybuddy.PayMyBuddy.model.Contact;
import com.paymybuddy.PayMyBuddy.service.interfaces.IContactService;
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
public class ContactServiceIT {
    
    @Autowired
    IContactService contactService;
   @Autowired
   public IDataBase dataBaseTest;
    
    @BeforeAll
    public void config(){
        Connection con= null;
        ScriptRunner sr = null;
        Reader reader = null;
        try {
            con = dataBaseTest.getConnection();
            sr=new ScriptRunner(con);
            reader = new BufferedReader(new FileReader("F:\\OPENCLASSROOMS\\PROJET 6\\PayMyBuddyGit\\PayMyBuddy\\src\\test\\java\\com\\paymybuddy\\PayMyBuddy\\integration\\config\\resources\\DataTest.sql"));
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sr.runScript(reader);
        
    }
    
    @Test
    @Order(1)
    public void createContactIT() {
        Contact contact=new Contact();
        contact.setUserIDContact(2);
        contact.setUserIDAccount(1);
        assertThat(contactService.createContact(contact)).isEqualTo(true);
    }
    @Test
    @Order(2)
    public void readContactListIT() {
        assertThat(asJsonString(contactService.readContactList())).isEqualTo("[{\"contactID\":1,\"userIDAccount\":1,\"userIDContact\":2}]");
    }
    @Test
    @Order(3)
    public void readUsersContactList(){
        assertThat(asJsonString(contactService.readUsersContactList(1))).isEqualTo("[{\"contactID\":1,\"userIDAccount\":1,\"userIDContact\":2}]");
    }
    
    @Test
    @Order(4)
    public void readContactIT(){
        assertThat(asJsonString(contactService.readContact(1))).isEqualTo("{\"contactID\":1,\"userIDAccount\":1,\"userIDContact\":2}");
    }
    
    @Test
    @Order(5)
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
