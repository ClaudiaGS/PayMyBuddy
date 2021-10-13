package com.paymybuddy.PayMyBuddy;

import com.paymybuddy.PayMyBuddy.controller.ContactController;
import com.paymybuddy.PayMyBuddy.model.Contact;
import com.paymybuddy.PayMyBuddy.service.interfaces.IContactService;
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

@SpringBootTest(classes = ContactController.class)
@EnableWebMvc
@AutoConfigureMockMvc
public class ContactControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    IContactService contactService;
    
    List<Contact> contactList = new ArrayList<>();
    Contact contact1 = new Contact();
    Contact contact2 = new Contact();
    
    @BeforeEach
    public void config() {
        contact1.setContactID(1);
        contact1.setUserIDContact(2);
        contact1.setUserIDAccount(1);
        contact2.setContactID(2);
        contact2.setUserIDContact(1);
        contact2.setUserIDAccount(2);
    }
    
    @Test
    public void readContactListTest() {
        try {
            when(contactService.readContactList()).thenReturn(contactList);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/readContactListInfo")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void readContactTest() {
        try {
            when(contactService.readContact(1)).thenReturn(contact1);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/readContactInfo")
                    .param("contactID", "1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void createContactTest() {
        
        Contact contact = new Contact();
        contact.setUserIDAccount(1);
        contact.setUserIDContact(3);
        try {
            when(contactService.createContact(contact)).thenReturn(true);
            this.mvc.perform(MockMvcRequestBuilders
                    .post("/createContact")
                    .param("userIDAccount", "1")
                    .param("userIDContact", "3")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void readUsersContactListTest() {
        List<Contact> usersContactList = new ArrayList<>();
        usersContactList.add(contact1);
        try {
            when(contactService.readUsersContactList(1)).thenReturn(usersContactList);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/readUsersContactListInfo")
                    .param("userIDAccount", "1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void deleteContactTest() {
        
        try {
            when(contactService.deleteContact(2)).thenReturn(true);
            this.mvc.perform(MockMvcRequestBuilders
                    .delete("/deleteContact")
                    .param("contactID", "2")
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}



