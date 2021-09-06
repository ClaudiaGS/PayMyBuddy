package com.paymybuddy.PayMyBuddy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseConfig {
    
    @Bean
    public DataBase createDatabase() {
        return new DataBase();
    }
}
