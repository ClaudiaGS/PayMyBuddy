package com.paymybuddy.PayMyBuddy.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DataBase implements IDataBase{
    
    private static final Logger logger = LogManager.getLogger("DataBaseConfig");
    private String url;
    private String user;
    private String password;
    
    public DataBase(final String url, final String user, final String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        logger.info("Create DB connection");
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
    @Override
    public void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
                logger.info("Closing DB connection");
            } catch (SQLException e) {
                logger.error("Error while closing connection", e);
            }
        }
    }
    @Override
    public void closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
                logger.info("Closing Prepared Statement");
            } catch (SQLException e) {
                logger.error("Error while closing prepared statement", e);
            }
        }
    }
    @Override
    public void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                logger.info("Closing Result Set");
            } catch (SQLException e) {
                logger.error("Error while closing result set", e);
            }
        }
    }
}
