package com.paymybuddy.PayMyBuddy.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableEncryptableProperties
public class DataBaseConfig {
    
    @Bean
    public DataBase createDatabase(final Environment env) {

        return new DataBase(env.getProperty("datasource.url"), env.getProperty("datasource.user"), env.getProperty("datasource.password"));
    }
}
