package com.paymybuddy.PayMyBuddy.integration.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class DataBaseTestConfig {
 
    @Bean
    @Primary
    public DataBaseTest createDatabaseTest() {
        return new DataBaseTest();
    }
}
