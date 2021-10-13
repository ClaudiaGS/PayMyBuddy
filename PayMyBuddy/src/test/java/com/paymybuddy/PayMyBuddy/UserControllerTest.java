package com.paymybuddy.PayMyBuddy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.controller.UserController;
import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserService;
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
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UserController.class)
@EnableWebMvc
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    IUserService userService;
    
    List<User> userList = new ArrayList<>();
    User user1 = new User();
    User user2 = new User();
    
    @BeforeEach()
    public void config() {
        user1.setUserID(1);
        user1.setUserFirstName("first1");
        user1.setUserLastName("last1");
        user2.setUserID(2);
        user2.setUserFirstName("first2");
        user2.setUserLastName("last2");
        userList.add(user1);
        userList.add(user2);
        
    }
    
    
    @Test
    public void readUserListTest() {
        try {
            when(userService.readUserList()).thenReturn(userList);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/readUserListInfo")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void readUserTest() {
        try {
            when(userService.readUser(1)).thenReturn(user1);
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/readUserInfo")
                    .param("userID", "1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void createUserTest() {
        User newUser = new User();
        newUser.setUserID(3);
        newUser.setUserFirstName("first3");
        newUser.setUserLastName("last3");
        userList.add(newUser);
        try {
            when(userService.createUser(newUser)).thenReturn(true);
            this.mvc.perform(MockMvcRequestBuilders
                    .post("/createUser")
                    .param("userFirstName", "first3")
                    .param("userLastName", "last3")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void updateUserTest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("LastName", "lastUpdated");
        when(userService.updateUser(1, params)).thenReturn(true);
        try {
            this.mvc.perform(MockMvcRequestBuilders
                    .put("/updateUser")
                    .param("userID", "1")
                    .content(asJsonString(params))
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void registrationTest(){
        User userToRegister=new User();
        userToRegister.setUserFirstName("firstName");
        userToRegister.setUserLastName("lastName");
        Account account=new Account();
        account.setAccountEmail("email");
        account.setAccountPassword("pass");

        try {
            when(userService.register(account,userToRegister)).thenReturn(true);
            this.mvc.perform(MockMvcRequestBuilders
                    .post("/register")
                    .param("email", "email")
                    .param("password", "pass")
                    .param("rePassword","pass")
                    .param("firstName","firstName")
                    .param("lastName","lastName")
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
