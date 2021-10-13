package com.paymybuddy.PayMyBuddy.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDataBase {
    public Connection getConnection() throws ClassNotFoundException, SQLException;
    public void closeConnection(Connection con);
    public void closePreparedStatement(PreparedStatement ps);
    public void closeResultSet(ResultSet rs);
}
