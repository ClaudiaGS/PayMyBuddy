package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.config.DataBase;
import com.paymybuddy.PayMyBuddy.constants.DataBaseConstants;
import com.paymybuddy.PayMyBuddy.model.Contact;
import com.paymybuddy.PayMyBuddy.repository.interfaces.IContactRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContactRepository implements IContactRepository {
    @Autowired
    DataBase dataBase;
    @Autowired
    Contact contact;
    private static final Logger logger = LogManager.getLogger("ContactRepository");
    
    
    private Contact processRow(ResultSet rs) throws SQLException {
        contact = new Contact();
        contact.setContactID(rs.getInt(1));
        contact.setUserIDAccount(rs.getInt(2));
        contact.setUserIDContact(rs.getInt(3));
        return contact;
    }
    
    /**
     * (non-javadoc)
     *
     * @see IContactRepository#createContact(Contact)
     */
    @Override
    public boolean createContact(Contact contact) {
        
        boolean result = false;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try (java.sql.Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.CREATE_CONTACT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, contact.getUserIDAccount());
            ps.setInt(2, contact.getUserIDContact());
            
            logger.debug(ps.toString());
            
            ps.execute();
            resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                contact.setContactID(resultSet.getInt(1));
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
     * @see IContactRepository#readContactList()
     */
    @Override
    public List<Contact> readContactList() {
        List<Contact> contactList = new ArrayList<Contact>();
        contact = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_CONTACT_LIST);
            
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            while (rs.next()) {
                contact = processRow(rs);
                contactList.add(contact);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        return contactList;
        
    }
    
    /**
     * (non-javadoc)
     *
     * @see IContactRepository#readUsersContactList(int)
     */
    @Override
    public List<Contact> readUsersContactList(int userIDAccount) {
        List<Contact> userscontactList = new ArrayList<Contact>();
        contact = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_USERS_CONTACT_LIST);
            ps.setInt(1, userIDAccount);
            
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            while (rs.next()) {
                contact = processRow(rs);
                userscontactList.add(contact);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        return userscontactList;
    }
    
    /**
     * (non-javadoc)
     *
     * @see IContactRepository#readContact(int)
     */
    @Override
    public Contact readContact(int contactID) {
        contact = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_CONTACT);
            ps.setInt(1, contactID);
            
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            if (rs.next()) {
                contact = processRow(rs);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        return contact;
    }
    
    /**
     * (non-javadoc)
     *
     * @see IContactRepository#updateContact(Contact)
     */
    @Override
    public boolean updateContact(Contact contact) {
        
        boolean executed = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.UPDATE_CONTACT);
            ps.setInt(1, contact.getUserIDContact());
            ps.setInt(2, contact.getUserIDAccount());
            ps.setInt(3, contact.getContactID());
            
            logger.debug(ps.toString());
            
            rs = ps.executeQuery();
            if (rs.next()) {
                executed = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closePreparedStatement(ps);
        }
        return executed;
    }
    
    /**
     * (non-javadoc)
     *
     * @see IContactRepository#deleteContact(int)
     */
    @Override
    public boolean deleteContact(int contactID) {
       
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean executed = false;
        List<Contact> contactList = readContactList();
        contact = readContact(contactID);
        contactList.remove(contact);
        
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.DELETE_CONTACT);
            ps.setInt(1, contactID);

            logger.debug(ps.toString());
            
            if (ps.executeUpdate() != 0) {
                executed = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            dataBase.closeResultSet(rs);
            dataBase.closePreparedStatement(ps);
        }
        return executed;
    }
}
