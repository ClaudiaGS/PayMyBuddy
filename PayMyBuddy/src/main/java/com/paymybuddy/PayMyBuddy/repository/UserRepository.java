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
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {
    private static final Logger logger = LogManager.getLogger("UserRepository");
    
    @Autowired
    public DataBase dataBase;
    
    private User processRow(final ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getInt(1));
        user.setUserFirstName(rs.getString(2));
        user.setUserLastName(rs.getString(3));
        user.setUserBirthdate(rs.getDate(4));
        return user;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.repository.interfaces.IUserRepository#createUser(User)
     */
    @Override
    public boolean createUser(final User user) {
        
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        boolean result = false;
        
        try (Connection con = dataBase.getConnection()) {
            
            ps = con.prepareStatement(DataBaseConstants.CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, user.getUserFirstName());
            ps.setString(2, user.getUserLastName());
            ps.setDate(3, user.getUserBirthdate());
            
            logger.debug(ps.toString());
            
            ps.execute();
            
            resultSet = ps.getGeneratedKeys();
            
            if (resultSet.next()) {
                user.setUserID(resultSet.getInt(1));
                result = true;
            }
            
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(resultSet);
            dataBase.closePreparedStatement(ps);
        }
        
        return result;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.repository.interfaces.IUserRepository#readUser(int)
     */
    @Override
    public User readUser(final int id) {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        
        try (Connection con = dataBase.getConnection()) {
            
            ps = con.prepareStatement(DataBaseConstants.READ_USER);
            ps.setInt(1, id);
            
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                user = processRow(rs);
            }
            
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
            
        }
        
        return user;
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.repository.interfaces.IUserRepository#readUserList()
     */
    @Override
    public List<User> readUserList() {
        
        List<User> userList = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try (Connection con = dataBase.getConnection()) {
            
            ps = con.prepareStatement(DataBaseConstants.READ_USER_LIST);
            
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                userList.add(processRow(rs));
            }
            
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        
        return userList;
        
    }
    
    /**
     * (non-javadoc)
     *
     * @see com.paymybuddy.PayMyBuddy.repository.interfaces.IUserRepository#updateUser(User)
     */
    @Override
    public boolean updateUser(final User user) {
        
        boolean result = false;
        PreparedStatement ps = null;
        
        try (Connection con = dataBase.getConnection()) {
            
            ps = con.prepareStatement(DataBaseConstants.UPDATE_USER);
            ps.setString(1, user.getUserFirstName());
            ps.setString(2, user.getUserLastName());
            ps.setDate(3, user.getUserBirthdate());
            ps.setInt(4, user.getUserID());
            
            logger.debug(ps.toString());
            
            result = (ps.executeUpdate() > 0);
            
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closePreparedStatement(ps);
        }
        
        return result;
    }
    
  
}
