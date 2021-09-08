package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.config.DataBase;
import com.paymybuddy.PayMyBuddy.constants.DataBaseConstants;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class OperationsRepository {
    
    private static final Logger logger = LogManager.getLogger("OperationsRepository");
    @Autowired
    DataBase dataBase;

    
    
    public String getPasswordEncoded(String password) {
        logger.info("Encode password");
        String passwordEncoded = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.GET_PASSWORD_ENCODED);
            ps.setString(1, password);
            rs = ps.executeQuery();
            if(rs.next()) {
                passwordEncoded =rs.getString(1);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        logger.info("Password encoded is " + passwordEncoded);
        return passwordEncoded;
    }
    
    
    
    
    }
    
  
    
}
