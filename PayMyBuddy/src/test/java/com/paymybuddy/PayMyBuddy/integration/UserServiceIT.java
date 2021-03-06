package com.paymybuddy.PayMyBuddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.integration.config.DataBaseTestConfig;
import com.paymybuddy.PayMyBuddy.model.Account;
import com.paymybuddy.PayMyBuddy.model.User;
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
public class UserServiceIT {
    
    @Autowired
    IUserService userService;
  
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
    public void readUserListIT(){
        
        assertThat(asJsonString(userService.readUserList())).isEqualTo("[{\"userID\":1,\"userFirstName\":\"firstname1\",\"userLastName\":\"lastname1\"},{\"userID\":2,\"userFirstName\":\"firstname2\",\"userLastName\":\"lastname2\"},{\"userID\":3,\"userFirstName\":\"firstname3\",\"userLastName\":\"lastname3\"}]");
    }
    
 
    @Test
    @Order(2)
    public void readUserIT(){
        assertThat(asJsonString(userService.readUser(1))).isEqualTo("{\"userID\":1,\"userFirstName\":\"firstname1\",\"userLastName\":\"lastname1\"}");
    }
    

    @Test
    @Order(3)
    public void createUserIT() {
        User user=new User();
        user.setUserFirstName("firstName4");
        user.setUserLastName("lastName4");
        assertThat(userService.createUser(user)).isEqualTo(true);
    }
   
    @Test
    @Order(4)
    public void updateUserIT(){
        HashMap<String,Object> params=new HashMap<>();
        params.put("firstName", "updatedFirstName");
        assertThat((userService.updateUser(1,params))).isEqualTo(true);
    }

    @Test
    @Order(5)
    public void registrationIT(){
        Account account=new Account();
        account.setAccountEmail("account10@email");
        account.setAccountPassword("password10");
        User user=new User();
        user.setUserFirstName("firstName10");
        user.setUserLastName("lastName10");
        assertThat((userService.register(account,user))).isEqualTo(true);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
