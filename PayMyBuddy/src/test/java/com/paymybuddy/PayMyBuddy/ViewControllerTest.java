package com.paymybuddy.PayMyBuddy;

import com.paymybuddy.PayMyBuddy.controller.ViewController;
import com.paymybuddy.PayMyBuddy.integration.config.DataBaseTestConfig;
import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.model.RegisterInfoView;
import com.paymybuddy.PayMyBuddy.service.interfaces.IContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(DataBaseTestConfig.class)
@SpringBootTest
@EnableWebMvc
@AutoConfigureMockMvc
public class ViewControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    ViewController viewController;
    
    @MockBean
    IContactService contactService;
    
    MockHttpSession session;
    @MockBean
    Model model;
    
    @Test
    public void connexionTest() {
        try {
            Account account = new Account();
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/login")
                    .param("account", "account")
                    .param("model", "model")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("Login"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void home() {
        try {
            Account account = new Account();
            double amount = 50;
            final ResultActions resultActions = this.mvc.perform(MockMvcRequestBuilders
                    .post("/home")
                    .param("model", "model")
                    .session(session)
                    .flashAttr("account", account)
                    
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("/home"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void homePageTest() {
        try {
            
            final ResultActions resultActions = this.mvc.perform(MockMvcRequestBuilders
                    .get("/homePage")
                    .param("model", "model")
                    .session(session)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("Home"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void profilePageTest() {
        try {
            final ResultActions resultActions = this.mvc.perform(MockMvcRequestBuilders
                    .get("/profile")
                    .param("model", "model")
                    .session(session)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("Profile"));
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void registrationTest() {
        try {
            final ResultActions resultActions = this.mvc.perform(MockMvcRequestBuilders
                    .get("/registration")
                    .param("model", "model")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("Registration"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void registerUserTest() {
        try {
            RegisterInfoView registerInfoView = new RegisterInfoView();
            final ResultActions resultActions = this.mvc.perform(MockMvcRequestBuilders
                    .post("/registerUser")
                    .flashAttr("registerInfoView", "registerInfoView")
                    .param("model", "model")
                    .session(session)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("Registration"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void contactViewTest() {
        try {
            final ResultActions resultActions = this.mvc.perform(MockMvcRequestBuilders
                    .post("/contacts")
                    .param("model", "model")
                    .session(session)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("Contacts"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void deleteContactTest() {
//        try {
//            when(contactService.deleteContact(1)).thenReturn(true);
//            MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders
//                    .get("/deleteContact/{id}", 1)
//                    .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andReturn();
//            assertThat(mvcResult.getModelAndView()).isEqualTo("redirect:/contacts");
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    
    @Test
    public void createContactTest() {
        try {
        
            final ResultActions resultActions = this.mvc.perform(MockMvcRequestBuilders
                    .post("/createContact")
                    .param("model", "model")
                    .session(session)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("addContact"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


