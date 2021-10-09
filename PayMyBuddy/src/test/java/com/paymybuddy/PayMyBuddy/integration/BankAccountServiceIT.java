package com.paymybuddy.PayMyBuddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.integration.config.DataBaseTestConfig;
import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService;
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
public class BankAccountServiceIT {
    
    @Autowired
    IBankAccountService bankAccountService;
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
    @Order(5)
    public void createBankAccountIT() {
        Connection con = null;
        try {
            con = dataBaseTest.getConnection();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        BankAccount bankAccount=new BankAccount();
        bankAccount.setBankAccountCurrency("EURO");
        bankAccount.setBankAccountAmount(1000);
        bankAccount.setUserID(1);
        assertThat(bankAccountService.createBankAccount(con, bankAccount)).isEqualTo(true);
    }
    @Test
    @Order(1)
    public void readBankAccountListIT() {
        assertThat(asJsonString(bankAccountService.readBankAccountList())).isEqualTo("[{\"bankAccountID\":1,\"bankAccountAmount\":1000.0,\"bankAccountCurrency\":\"EURO\",\"userID\":1},{\"bankAccountID\":2,\"bankAccountAmount\":2000.0,\"bankAccountCurrency\":\"EURO\",\"userID\":2},{\"bankAccountID\":3,\"bankAccountAmount\":3000.0,\"bankAccountCurrency\":\"EURO\",\"userID\":3}]");
    }
    @Test
    @Order(2)
    public void readUsersBankAccountIT() {
        assertThat(asJsonString(bankAccountService.readUsersBankAccount(1))).isEqualTo("{\"bankAccountID\":1,\"bankAccountAmount\":1000.0,\"bankAccountCurrency\":\"EURO\",\"userID\":1}");
    }
    
    @Test
    @Order(3)
    public void readBankAccountIT() {
        assertThat(asJsonString(bankAccountService.readBankAccount(1))).isEqualTo("{\"bankAccountID\":1,\"bankAccountAmount\":1000.0,\"bankAccountCurrency\":\"EURO\",\"userID\":1}");
    }
    @Test
    @Order(4)
    public void updateAmountSenderIT(){
        assertThat((bankAccountService.updateAmount(1,50,"subtract"))).isEqualTo(949.75);
    }
    @Test
    @Order(6)
    public void updateAmountReceiverIT(){
        assertThat((bankAccountService.updateAmount(1,10,"add"))).isEqualTo(1010);
    }
    @Test
    @Order(7)
    public void updateAmountPersonalAccountAddIT(){
        assertThat(bankAccountService.updateAmountPersonalAccount(1000,50,"add")).isEqualTo(1050);
    }
    @Test
    @Order(8)
    public void updateAmountPersonalAccountSubtractIT(){
        assertThat(bankAccountService.updateAmountPersonalAccount(1000,50,"subtract")).isEqualTo(950);
    }
    @Test
    @Order(9)
    public void updateBankAccountIT() {
        Connection con= null;
        try {
            con = dataBaseTest.getConnection();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("bankAccountAmount", (double)5000);
        assertThat(bankAccountService.updateBankAccount(con, 1, params)).isEqualTo(true);
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
