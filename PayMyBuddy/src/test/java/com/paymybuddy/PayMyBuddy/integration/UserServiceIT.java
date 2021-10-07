package com.paymybuddy.PayMyBuddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.integration.config.DataBaseTestConfig;
import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.service.interfaces.IUserService;
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
public class UserServiceIT {
    
    @Autowired
    IUserService userService;
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
    public void createUser() {
        User user=new User();
        user.setUserFirstName("firstName4");
        user.setUserLastName("lastName4");
        assertThat(userService.createUser(user)).isEqualTo(true);
    }
    @Test
    public void readUserListIT(){
        assertThat(asJsonString(userService.readUserList())).isEqualTo("[{\"userID\":1,\"userFirstName\":\"firstname1\",\"userLastName\":\"lastname1\"},{\"userID\":2,\"userFirstName\":\"firstname2\",\"userLastName\":\"lastname2\"},{\"userID\":3,\"userFirstName\":\"firstname3\",\"userLastName\":\"lastname3\"},{\"userID\":4,\"userFirstName\":\"firstname4\",\"userLastName\":\"lastname4\"}]");
    }
    @Test
    public void readUserIT(){
        assertThat(asJsonString(userService.readUser(1))).isEqualTo("{\"userID\":1,\"userFirstName\":\"firstname1\",\"userLastName\":\"lastname1\"}");
    }
    @Test
    public void updateUserIT(){
        HashMap<String,Object> params=new HashMap<>();
        params.put("firstName", "updatedFirstName");
        assertThat((userService.updateUser(1,params))).isEqualTo(true);
    }
    @Test
    public void registrationIT(){
        Account account=new Account();
        account.setAccountEmail("account5@email");
        account.setAccountPassword("password5");
        User user=new User();
        user.setUserFirstName("firstName5");
        user.setUserLastName("lastName5");
        assertThat(asJsonString(userService.registration(account,user))).isEqualTo(true);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
