package com.paymybuddy.PayMyBuddy.constants;

public class DataBaseConstants {
    
    public static final String CREATE_USER = "insert INTO user(USER_FIRST_NAME, USER_LAST_NAME,USER_BIRTHDATE,USER_PROFILE_PICTURE) VALUES (?,?,?,?)";
  //  public static final String CREATE_ACCOUNT= "insert INTO account(ACCOUNT_EMAIL, ACCOUNT_PASSWORD,USER_ID) VALUES (?,SHA(?), (select max(USER_ID)+1 from user))";
  public static final String CREATE_ACCOUNT= "insert INTO account(ACCOUNT_EMAIL, ACCOUNT_PASSWORD,USER_ID) VALUES (?,SHA(?),?)";
  public static final String CREATE_BANK_ACCOUNT= "insert INTO bank_account(BANK_ACCOUNT_NUMBER, BANK_ACCOUNT_AMOUNT, BANK_ACCOUNT_CURRENCY,USER_ID) VALUES (?,?,?,?)";
  
  
  public static final String READ_USER = "select u.USER_ID,u.USER_FIRST_NAME, u.USER_LAST_NAME,u.USER_BIRTHDATE,u.USER_PROFILE_PICTURE from user u WHERE u.USER_ID=?";
    public static final String READ_BANK_ACCOUNT = "select b.BANK_ACCOUNT_ID,b.BANK_ACCOUNT_NUMBER, b.BANK_ACCOUNT_AMOUNT,b.BANK_ACCOUNT_CURRENCY,b.USER_ID from bank_account b WHERE b.BANK_ACCOUNT_ID=?";
    public static final String READ_ACCOUNT = "select a.ACCOUNT_ID,a.ACCOUNT_EMAIL, a.ACCOUNT_PASSWORD,a.USER_ID from account a WHERE a.ACCOUNT_ID=?";
    
    public static final String READ_USER_LIST = "select * from user";
    public static final String READ_BANK_ACCOUNT_LIST = "select * from bank_account";
    public static final String READ_ACCOUNT_LIST = "select * from account";
    
    public static final String UPDATE_USER = "update user set USER_FIRST_NAME=?, USER_LAST_NAME=?, USER_BIRTHDATE=?, USER_PROFILE_PICTURE=? where USER_ID=?";
  public static final String UPDATE_ACCOUNT = "update account set ACCOUNT_EMAIL=?, ACCOUNT_PASSWORD=SHA(?), USER_ID=? where ACCOUNT_ID=?";
  
  public static final String UPDATE_BANK_ACCOUNT = "update bank_account set BANK_ACCOUNT_NUMBER=?, BANK_ACCOUNT_AMOUNT, BANK_ACCOUNT_CURRENCY where BANK_ACCOUNT_ID=?";
    
    
}

