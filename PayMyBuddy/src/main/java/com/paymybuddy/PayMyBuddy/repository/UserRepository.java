package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.config.DataBase;
import com.paymybuddy.PayMyBuddy.constants.DataBaseConstants;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.interfaces.IUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public DataBase dataBase;
    
    @Override
    public boolean createUser(User newUser) {
        logger.info("Create user");
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        boolean result = false;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newUser.getUserFirstName());
            ps.setString(2, newUser.getUserLastName());
            ps.setDate(3, newUser.getUserBirthdate());
            ps.execute();
            resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                newUser.setUserID(resultSet.getInt(1));
                result = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(resultSet);
            dataBase.closePreparedStatement(ps);
            return result;
        }
    }
    
    @Override
    public User readUser(int userID) {
        logger.info("Reading user info for id " + userID);
        user = new User();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_USER);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = processRow(rs);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
            return user;
        }
    }
    
    @Override
    public List<User> readUserList() {
        logger.info("Reading user list from table");
        List<User> userList = new ArrayList<User>();
        user = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_USER_LIST);
            rs = ps.executeQuery();
            while (rs.next()) {
                user = processRow(rs);
                userList.add(user);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        return userList;
        
    }
    
    protected User processRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getInt(1));
        user.setUserFirstName(rs.getString(2));
        user.setUserLastName(rs.getString(3));
        user.setUserBirthdate(rs.getDate(4));
        // user.setUserProfilePicture(rs.getObject(5, Image.class));
        return user;
    }
    
    public boolean updateUser(int userID, HashMap<String, Object> params) {
        logger.info("Updating user with id " + userID);
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
                    user.setUserBirthdate((Date) entry.getValue());
                    break;
//                case "userProfilePicture":
//                    user.setUserProfilePicture((Image) entry.getValue());
                default:
                    logger.warn("Trying to modify unexisting user parameter");
                    break;
            }
        }
        boolean executed = false;
        PreparedStatement ps = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.UPDATE_USER);
            ps.setString(1, user.getUserFirstName());
            ps.setString(2, user.getUserLastName());
            ps.setDate(3, user.getUserBirthdate());
            // ps.setBlob(4, (Blob) user.getUserProfilePicture());
            ps.setInt(4, user.getUserID());
            ps.execute();
            executed = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closePreparedStatement(ps);
        }
        return executed;
    }
}
