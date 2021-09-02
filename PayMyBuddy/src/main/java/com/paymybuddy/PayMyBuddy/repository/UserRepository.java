package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.config.DataBase;
import com.paymybuddy.PayMyBuddy.constants.DataBaseConstants;
import com.paymybuddy.PayMyBuddy.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository implements IUserRepository {
    @Autowired
    User user;
    private static final Logger logger = LogManager.getLogger("UserRepository");
    
    @Autowired
    public DataBase dataBaseConfig;
    
    @Override
    public boolean createUser(User newUser) {
        logger.info("Create user");
        ResultSet resultSet = null;
        boolean result = false;
        try(Connection con = dataBaseConfig.getConnection()) {
            
            PreparedStatement ps = con.prepareStatement(DataBaseConstants.CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newUser.getUserFirstName());
            ps.setString(2, newUser.getUserLastName());
            ps.setDate(3, (Date) newUser.getUserBirthdate());
            ps.setBlob(4, (Blob) newUser.getUserProfilePicture());
            ps.execute();
            
            resultSet =  ps.getGeneratedKeys();
            
            if( resultSet.next() ) {
                newUser.setUserID(resultSet.getInt(1));
                result = true;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }
    
    @Override
    public User readUser(int userID) {
        logger.info("Reading user info for id "+userID);
        Connection con = null;
        user = null;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DataBaseConstants.GET_USER);
            ps.setString(1, String.valueOf(userID));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = processRow(rs);
            }
            rs.close();
            //dataBaseConfig.closeResultSet(rs);
            // dataBaseConfig.closePreparedStatement(ps);
            ps.close();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            dataBaseConfig.closeConnection(con);
            return user;
        }
    }
    
    
    @Override
    public List<User> readUserList() {
        logger.info("Reading user list from table");
        List<User> userList = new ArrayList<User>();
        user = null;
        Connection c = null;
        try {
            c = dataBaseConfig.getConnection();
            PreparedStatement ps = c.prepareStatement(DataBaseConstants.GET_USER_LIST);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = processRow(rs);
                userList.add(user);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            dataBaseConfig.closeConnection(c);
        }
        return userList;
        
    }
    
    protected User processRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getInt(1));
        user.setUserFirstName(rs.getString(2));
        user.setUserLastName(rs.getString(3));
        user.setUserBirthdate(rs.getDate(4));
        //  user.setUserProfilePicture(rs.getObject(5, Image.class));
        return user;
    }
    
    public User updateUser(int userID, HashMap<String, Object> params) {
        logger.info("Updating user id "+userID);
        user = readUser(userID);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case "firstName":
                    user.setUserFirstName(entry.getValue().toString());
                    break;
                case "lastName":
                    user.setUserLastName(entry.getValue().toString());
                    break;
                case "birthdate":
                    user.setUserBirthdate((Date) (entry.getValue()));
                    break;
                case "userProfilePicture":
                    user.setUserProfilePicture((Image) entry.getValue());
                default:
                    logger.warn("Trying to modify unexisting user parameter");
                    break;
            }
        }
        
        Connection con = null;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DataBaseConstants.MODIFY_USER);
            ps.setString(1, user.getUserFirstName());
            ps.setString(2, user.getUserLastName());
            ps.setDate(3, (Date) user.getUserBirthdate());
            ps.setBlob(4, (Blob) user.getUserProfilePicture());
            ps.setInt(5, user.getUserID());
            ps.execute();
            ps.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return user;
    }
    
}
