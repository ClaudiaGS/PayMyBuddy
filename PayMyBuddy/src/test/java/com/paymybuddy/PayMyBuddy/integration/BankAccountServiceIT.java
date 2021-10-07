package com.paymybuddy.PayMyBuddy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.PayMyBuddy.config.IDataBase;
import com.paymybuddy.PayMyBuddy.integration.config.DataBaseTestConfig;
import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.service.interfaces.IBankAccountService;
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
public class BankAccountServiceIT {
    
    @Autowired
    IBankAccountService bankAccountService;
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
    public void createBankAccountIT() {
        BankAccount bankAccount=new BankAccount();
        bankAccount.setBankAccountCurrency("EURO");
        bankAccount.setBankAccountAmount(1000);
        bankAccount.setUserID(1);
        assertThat(bankAccountService.createBankAccount(testConnection, bankAccount)).isEqualTo(true);
    }
    @Test
    public void readBankAccountListIT() {
        assertThat(asJsonString(bankAccountService.readBankAccountList())).isEqualTo("[{\"bankAccountID\":1,\"bankAccountAmount\":1000,\"bankAccountCurrency\":\"EURO\",\"userID\":1}]");
    }
    @Test
    public void readUsersBankAccountIT() {
        assertThat(asJsonString(bankAccountService.readUsersBankAccount(1))).isEqualTo("[{\"bankAccountID\":1,\"bankAccountAmount\":1000,\"bankAccountCurrency\":\"EURO\",\"userID\":1}]");
    }
    
    @Test
    public void readBankAccountIT() {
        assertThat(asJsonString(bankAccountService.readBankAccount(1))).isEqualTo("[{\"bankAccountID\":1,\"bankAccountAmount\":1205.5,\"bankAccountCurrency\":\"EURO\",\"userID\":1}]");
    }
    @Test
    public void updateAmountSenderIT(){
        assertThat((bankAccountService.updateAmount(1,50,"subtract"))).isEqualTo(949.75);
    }
    @Test
    public void updateAmountReceiverIT(){
        assertThat((bankAccountService.updateAmount(1,10,"add"))).isEqualTo(959.75);
    }
    @Test
    public void updateAmountPersonalAccountAddIT(){
        assertThat(bankAccountService.updateAmountPersonalAccount(1000,50,"add")).isEqualTo(1050);
    }
    @Test
    public void updateAmountPersonalAccountSubtractIT(){
        assertThat(bankAccountService.updateAmountPersonalAccount(1000,50,"subtract")).isEqualTo(950);
    }
    @Test
    public void updateBankAccountIT() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("bankAccountAmount", 5000);
        assertThat(asJsonString(bankAccountService.updateBankAccount(testConnection, 1, params))).isEqualTo(true);
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
