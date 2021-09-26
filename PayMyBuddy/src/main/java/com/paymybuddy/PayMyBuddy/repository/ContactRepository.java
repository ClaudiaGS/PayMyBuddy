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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ContactRepository implements IContactRepository {
    @Autowired
    DataBase dataBase;
    @Autowired
    Contact contact;
    private static final Logger logger = LogManager.getLogger("ContactRepository");
    
    @Override
    public Contact createContact(int userIDAccount, int userIDContact) {
        contact = new Contact();
        logger.info("Create contact with user id: " + userIDContact);
        List<Contact> contactList = readContactList();
        int contactID = -1;
        for (Contact c : contactList) {
            if (c.getUserIDAccount() == userIDAccount && c.getUserIDContact() == userIDContact) {
                contactID = c.getContactID();
            }
        }
        if (contactID <= 0) {
            PreparedStatement ps = null;
            ResultSet resultSet = null;
            try (java.sql.Connection con = dataBase.getConnection()) {
                ps = con.prepareStatement(DataBaseConstants.CREATE_CONTACT, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userIDAccount);
                ps.setInt(2, userIDContact);
                ps.execute();
                resultSet = ps.getGeneratedKeys();
                if (resultSet.next()) {
                    contact.setContactID(resultSet.getInt(1));
                    contact.setUserIDAccount(userIDAccount);
                    contact.setUserIDContact(userIDContact);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                dataBase.closeResultSet(resultSet);
                dataBase.closePreparedStatement(ps);
            }
        } else {
            logger.info("User " + userIDAccount + " already has user " + userIDContact + " in his contact list");
            contact=null;
        }
        return contact;
    }
    
    @Override
    public List<Contact> readContactList() {
        logger.info("Reading contact list from table");
        List<Contact> contactList = new ArrayList<Contact>();
        contact = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_CONTACT_LIST);
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
    
    @Override
    public List<Contact> readUsersContactList(int userIDAccount) {
        logger.info("Reading contact list for user with id " + userIDAccount);
        List<Contact> userscontactList = new ArrayList<Contact>();
        contact = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_USERS_CONTACT_LIST);
            ps.setInt(1, userIDAccount);
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
    
    protected Contact processRow(ResultSet rs) throws SQLException {
        contact = new Contact();
        contact.setContactID(rs.getInt(1));
        contact.setUserIDAccount(rs.getInt(2));
        contact.setUserIDContact(rs.getInt(3));
        return contact;
    }
    
    @Override
    public Contact readContact(int contactID) {
        logger.info("Reading contact with id: " + contactID);
        contact = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.READ_CONTACT);
            ps.setInt(1, contactID);
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
    
    @Override
    public boolean updateContact(int contactID, HashMap<String, Object> params) {
        logger.info("Update contact with id " + contactID);
        boolean executed = false;
        contact = readContact(contactID);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case "userIDAccount":
                    contact.setUserIDContact((int) entry.getValue());
                    break;
                case "userIDContact":
                    contact.setUserIDContact((int) entry.getValue());
                    break;
                default:
                    logger.warn("Trying to modify unexisting contact parameter");
                    break;
            }
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.UPDATE_CONTACT);
            ps.setInt(1, contact.getUserIDContact());
            ps.setInt(2, contact.getUserIDAccount());
            ps.setInt(3, contactID);
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
    
    @Override
    public boolean deleteContact(int contactID) {
        logger.info("Deleting contact with ID" + contactID);
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean executed = false;
        List<Contact> contactList = readContactList();
        contact = readContact(contactID);
        contactList.remove(contact);
        
        try (Connection con = dataBase.getConnection()) {
            ps = con.prepareStatement(DataBaseConstants.DELETE_CONTACT);
            ps.setInt(1, contactID);
//            ps.executeUpdate();
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
