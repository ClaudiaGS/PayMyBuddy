/* Setting up PayMyBuddy DB */
create database paymybuddyDB;
use paymybuddyDB;

create table IF NOT EXISTS account (
ACCOUNT_ID int PRIMARY KEY AUTO_INCREMENT,
ACCOUNT_EMAIL varchar(30) UNIQUE,
ACCOUNT_PASSWORD varchar(1000),
USER_ID int,
FOREIGN KEY (USER_ID) REFERENCES user(USER_ID)
)ENGINE=INNODB;

create table IF NOT EXISTS user(
USER_ID int PRIMARY KEY AUTO_INCREMENT,
USER_FIRST_NAME varchar(30),
USER_LAST_NAME varchar(30),
USER_BIRTHDATE date,
USER_PROFILE_PICTURE longblob
)ENGINE=INNODB;

create table IF NOT EXISTS bank_account(
BANK_ACCOUNT_ID int PRIMARY KEY AUTO_INCREMENT,
BANK_ACCOUNT_NUMBER int,
BANK_ACCOUNT_AMOUNT double NOT NULL,
BANK_ACCOUNT_CURRENCY varchar(10),
USER_ID int NOT NULL,
FOREIGN KEY (USER_ID) REFERENCES user(USER_ID)
)ENGINE=INNODB;

create table IF NOT EXISTS transaction(
TRANSACTION_ID int PRIMARY KEY AUTO_INCREMENT,
TRANSACTION_DESCRIPTION varchar(100),
TRANSACTION_DEBITED_AMOUNT double,
TRANSACTION_FEE_AMOUNT double,
TRANSACTION_RECEIVED_AMOUNT double,
USER_ID_SENDER int NOT NULL,
USER_ID_RECEIVER int NOT NULL,
FOREIGN KEY (USER_ID_SENDER) REFERENCES user(USER_ID),
FOREIGN KEY (USER_ID_RECEIVER) REFERENCES user(USER_ID)
)ENGINE=INNODB;

create table IF NOT EXISTS connection(
CONNECTION_ID int PRIMARY KEY AUTO_INCREMENT,
USER_ID_ACCOUNT int,
USER_ID_CONTACT int,
FOREIGN KEY (USER_ID_ACCOUNT) REFERENCES user(USER_ID),
FOREIGN KEY (USER_ID_CONTACT) REFERENCES user(USER_ID)
) ENGINE=INNODB;


insert into user(USER_FIRST_NAME,USER_LAST_NAME,USER_BIRTHDATE) values('firstname1','lastname1','2001-01-01');
insert into user(USER_FIRST_NAME,USER_LAST_NAME,USER_BIRTHDATE) values('firstname2','lastname2','2002-02-02');
insert into user(USER_FIRST_NAME,USER_LAST_NAME,USER_BIRTHDATE) values('firstname3','lastname3','2003-03-03');

insert into bank_account(BANK_ACCOUNT_NUMBER,BANK_ACCOUNT_AMOUNT,BANK_ACCOUNT_CURRENCY,USER_ID) values('333333333','11.1','EURO','3');
insert into bank_account(BANK_ACCOUNT_NUMBER,BANK_ACCOUNT_AMOUNT,BANK_ACCOUNT_CURRENCY,USER_ID) values('222222222','22.2','EURO','2');
insert into bank_account(BANK_ACCOUNT_NUMBER,BANK_ACCOUNT_AMOUNT,BANK_ACCOUNT_CURRENCY,USER_ID) values('111111112','52.2','EURO','2');

insert into account(ACCOUNT_EMAIL,ACCOUNT_PASSWORD,USER_ID) values ('account1@email',SHA('password1'), '1');
insert into account(ACCOUNT_EMAIL,ACCOUNT_PASSWORD,USER_ID) values ('account2@email',SHA('password2'), '2');
insert into account(ACCOUNT_EMAIL,ACCOUNT_PASSWORD,USER_ID) values ('account3@email',SHA('password3'), '3');

