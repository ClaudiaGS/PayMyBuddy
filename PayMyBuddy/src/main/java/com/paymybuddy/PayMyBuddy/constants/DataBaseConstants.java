package com.paymybuddy.PayMyBuddy.constants;

public class DataBaseConstants {
    
    public static final String CREATE_USER = "insert INTO user(USER_FIRST_NAME, USER_LAST_NAME,USER_BIRTHDATE) VALUES (?,?,?)";
    //  public static final String CREATE_ACCOUNT= "insert INTO account(ACCOUNT_EMAIL, ACCOUNT_PASSWORD,USER_ID) VALUES (?,SHA(?), (select max(USER_ID)+1 from user))";
    public static final String CREATE_ACCOUNT = "insert INTO account(ACCOUNT_EMAIL, ACCOUNT_PASSWORD,USER_ID) VALUES (?,SHA(?),?)";
    public static final String CREATE_BANK_ACCOUNT = "insert INTO bank_account(BANK_ACCOUNT_NUMBER, BANK_ACCOUNT_AMOUNT, BANK_ACCOUNT_CURRENCY,USER_ID) VALUES (?,?,?,?)";
    public static final String CREATE_CONTACT = "insert INTO contact(USER_ID_ACCOUNT, USER_ID_CONTACT) VALUES (?,?)";
    public static final String CREATE_TRANSACTION = "insert INTO transaction(TRANSACTION_DESCRIPTION, TRANSACTION_DEBITED_AMOUNT,TRANSACTION_FEE_AMOUNT, TRANSACTION_RECEIVED_AMOUNT,USER_ID_SENDER, USER_ID_RECEIVER) VALUES (?,?,?,?,?,?)";
    
    public static final String READ_USER = "select * from user WHERE USER_ID=?";
    public static final String READ_BANK_ACCOUNT = "select * from bank_account WHERE BANK_ACCOUNT_ID=?";
    public static final String READ_ACCOUNT = "select a.ACCOUNT_ID,a.ACCOUNT_EMAIL, a.ACCOUNT_PASSWORD,a.USER_ID from account a WHERE a.ACCOUNT_ID=?";
    public static final String READ_CONTACT = "select c.CONTACT_ID, c.USER_ID_CONTACT, c.USER_ID_ACCOUNT, c.USER_ID_CONTACT from contact c WHERE c.CONTACT_ID=?";
    public static final String READ_TRANSACTION = "select * from transaction where TRANSACTION_ID=?";
    
    public static final String READ_ACCOUNT_EMAIL_BASED = "select * from account where ACCOUNT_EMAIL=?";
    
    
    public static final String READ_USERS_CONTACT_LIST = "select from contact WHERE USER_ID_ACCOUNT=?";
    public static final String READ_CONTACT_LIST = "select * from contact";
    public static final String READ_USER_LIST = "select * from user";
    public static final String READ_BANK_ACCOUNT_LIST = "select * from bank_account";
    public static final String READ_ACCOUNT_LIST = "select * from account";
    public static final String READ_TRANSACTION_LIST = "select * from transaction";
    public static final String READ_USERS_TRANSACTION_LIST = "select from transaction where USER_ID_SENDER=?";
    
    
    public static final String UPDATE_USER = "update user set USER_FIRST_NAME=?, USER_LAST_NAME=?, USER_BIRTHDATE=? where USER_ID=?";
    public static final String UPDATE_ACCOUNT = "update account set ACCOUNT_EMAIL=?, ACCOUNT_PASSWORD=SHA(?), USER_ID=? where ACCOUNT_ID=?";
    public static final String UPDATE_BANK_ACCOUNT = "update bank_account set BANK_ACCOUNT_NUMBER=?, BANK_ACCOUNT_AMOUNT=?, BANK_ACCOUNT_CURRENCY=? where BANK_ACCOUNT_ID=?";
    public static final String UPDATE_CONTACT = "update contact set USER_ID_ACCOUNT=?,USER_ID_CONTACT=? where CONTACT_ID=?";
    
    public static final String DELETE_CONTACT = "delete from contact where CONTACT_ID=?";
    public static final String DELETE_TRANSACTION = "delete from transaction where TRANSACTION_ID=?";
    
    public static final String AUTHENTIFICATE="select * from account WHERE ACCOUNT_EMAIL=? AND ACCOUNT_PASSWORD=SHA(?)";
//    public static final String GET_PASSWORD_ENCODED = "select SHA(?)";
    
}

