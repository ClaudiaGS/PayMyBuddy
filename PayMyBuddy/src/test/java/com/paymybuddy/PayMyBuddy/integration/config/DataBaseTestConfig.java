package com.paymybuddy.PayMyBuddy.integration.config;

import com.paymybuddy.PayMyBuddy.config.IDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class DataBaseTestConfig {
    @Autowired
    private IDataBase dataBaseTest;
    @Bean
    @Primary
    public IDataBase createDatabaseTest() {
        return new DataBaseTest();
    }
}
