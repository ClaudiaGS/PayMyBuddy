//package com.paymybuddy.PayMyBuddy.integration.config;
//
//import com.paymybuddy.PayMyBuddy.config.DataBase;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//
//@TestConfiguration
//public class DataBaseTestConfig {
//    @Autowired
//    private DataBase dataBase;
//    @Bean
//    @Primary
//    public DataBase createDatabase() {
//        return new DataBase();
//    }
//}
//
//
////public class DataTestConfig {
////    @Autowired
////    private DataSources dataSources;
////    @Bean
////    @Primary
////    public IRecoveredData createRecoveredTestData() {
////        IRecoveredData dataTestRecovery = new RecoveredTestData();
////        dataTestRecovery.readData(dataSources);
////        return dataTestRecovery;
////    }
////
//
//}