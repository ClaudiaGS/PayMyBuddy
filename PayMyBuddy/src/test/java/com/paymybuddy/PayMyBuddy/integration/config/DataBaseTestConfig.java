package com.paymybuddy.PayMyBuddy.integration.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@TestConfiguration
@EnableEncryptableProperties
public class DataBaseTestConfig {
 
    @Bean
    @Primary
    public DataBaseTest createDatabaseTest(final Environment env) {
        return new DataBaseTest(env.getProperty("datasourcetest.url"), env.getProperty("datasource.user"), env.getProperty("datasource.password"));
    }
}
