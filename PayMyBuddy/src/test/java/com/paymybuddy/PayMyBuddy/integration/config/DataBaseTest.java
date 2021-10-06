package com.paymybuddy.PayMyBuddy.integration.config;

import com.paymybuddy.PayMyBuddy.config.IDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseTest implements IDataBase {
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return null;
    }
    
    @Override
    public void closeConnection(Connection con) {
    
    }
    
    @Override
    public void closePreparedStatement(PreparedStatement ps) {
    
    }
    
    @Override
    public void closeResultSet(ResultSet rs) {
    
    }
}
