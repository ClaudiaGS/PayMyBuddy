/* Setting up PayMyBuddy DB */
create database paymybuddyDB;
use paymybuddyDB;



create table IF NOT EXISTS user(
USER_ID int PRIMARY KEY AUTO_INCREMENT,
USER_FIRST_NAME varchar(30) NOT NULL,
USER_LAST_NAME varchar(30) NOT NULL
)ENGINE=INNODB;

create table IF NOT EXISTS account (
ACCOUNT_ID int PRIMARY KEY AUTO_INCREMENT,
ACCOUNT_EMAIL varchar(30) UNIQUE NOT NULL,
ACCOUNT_PASSWORD varchar(1000) NOT NULL,
USER_ID int NOT NULL,
FOREIGN KEY (USER_ID) REFERENCES user(USER_ID)
)ENGINE=INNODB;

create table IF NOT EXISTS bank_account(
BANK_ACCOUNT_ID int PRIMARY KEY AUTO_INCREMENT,
BANK_ACCOUNT_AMOUNT DECIMAL(10,2) NOT NULL,
BANK_ACCOUNT_CURRENCY varchar(10) NOT NULL,
USER_ID int NOT NULL,
FOREIGN KEY (USER_ID) REFERENCES user(USER_ID)
)ENGINE=INNODB;

create table IF NOT EXISTS transaction(
TRANSACTION_ID int PRIMARY KEY AUTO_INCREMENT,
TRANSACTION_DESCRIPTION varchar(100),
TRANSACTION_DEBITED_AMOUNT DECIMAL(10,2) NOT NULL,
TRANSACTION_FEE_AMOUNT DECIMAL(10,2) NOT NULL,
TRANSACTION_RECEIVED_AMOUNT DECIMAL(10,2) NOT NULL,
USER_ID_SENDER int NOT NULL,
USER_ID_RECEIVER int NOT NULL,
FOREIGN KEY (USER_ID_SENDER) REFERENCES user(USER_ID),
FOREIGN KEY (USER_ID_RECEIVER) REFERENCES user(USER_ID)
)ENGINE=INNODB;

create table IF NOT EXISTS contact(
CONTACT_ID int PRIMARY KEY AUTO_INCREMENT,
USER_ID_ACCOUNT int NOT NULL,
USER_ID_CONTACT int NOT NULL,
FOREIGN KEY (USER_ID_ACCOUNT) REFERENCES user(USER_ID),
FOREIGN KEY (USER_ID_CONTACT) REFERENCES user(USER_ID)
) ENGINE=INNODB;


insert into user(USER_FIRST_NAME,USER_LAST_NAME) values('firstname1','lastname1');
insert into user(USER_FIRST_NAME,USER_LAST_NAME) values('firstname2','lastname2');
insert into user(USER_FIRST_NAME,USER_LAST_NAME) values('firstname3','lastname3');
insert into user(USER_FIRST_NAME,USER_LAST_NAME) values('firstname4','lastname4');
insert into user(USER_FIRST_NAME,USER_LAST_NAME) values('firstname5','lastname5');
insert into user(USER_FIRST_NAME,USER_LAST_NAME) values('firstname6','lastname6');
insert into user(USER_FIRST_NAME,USER_LAST_NAME) values('firstname7','lastname7');
insert into user(USER_FIRST_NAME,USER_LAST_NAME) values('firstname8','lastname8');
insert into user(USER_FIRST_NAME,USER_LAST_NAME) values('firstname9','lastname9');
insert into user(USER_FIRST_NAME,USER_LAST_NAME) values('firstname10','lastname10');


insert into bank_account(BANK_ACCOUNT_AMOUNT,BANK_ACCOUNT_CURRENCY,USER_ID) values('1000','EURO','1');
insert into bank_account(BANK_ACCOUNT_AMOUNT,BANK_ACCOUNT_CURRENCY,USER_ID) values('2000','EURO','2');
insert into bank_account(BANK_ACCOUNT_AMOUNT,BANK_ACCOUNT_CURRENCY,USER_ID) values('3000','EURO','3');
insert into bank_account(BANK_ACCOUNT_AMOUNT,BANK_ACCOUNT_CURRENCY,USER_ID) values('4000','EURO','4');
insert into bank_account(BANK_ACCOUNT_AMOUNT,BANK_ACCOUNT_CURRENCY,USER_ID) values('5000','EURO','5');
insert into bank_account(BANK_ACCOUNT_AMOUNT,BANK_ACCOUNT_CURRENCY,USER_ID) values('6000','EURO','6');
insert into bank_account(BANK_ACCOUNT_AMOUNT,BANK_ACCOUNT_CURRENCY,USER_ID) values('7000','EURO','7');
insert into bank_account(BANK_ACCOUNT_AMOUNT,BANK_ACCOUNT_CURRENCY,USER_ID) values('8000','EURO','8');
insert into bank_account(BANK_ACCOUNT_AMOUNT,BANK_ACCOUNT_CURRENCY,USER_ID) values('9000','EURO','9');
insert into bank_account(BANK_ACCOUNT_AMOUNT,BANK_ACCOUNT_CURRENCY,USER_ID) values('10000','EURO','10');

insert into account(ACCOUNT_EMAIL,ACCOUNT_PASSWORD,USER_ID) values ('account1@email',SHA('password1'), '1');
insert into account(ACCOUNT_EMAIL,ACCOUNT_PASSWORD,USER_ID) values ('account2@email',SHA('password2'), '2');
insert into account(ACCOUNT_EMAIL,ACCOUNT_PASSWORD,USER_ID) values ('account3@email',SHA('password3'), '3');
insert into account(ACCOUNT_EMAIL,ACCOUNT_PASSWORD,USER_ID) values ('account4@email',SHA('password4'), '4');
insert into account(ACCOUNT_EMAIL,ACCOUNT_PASSWORD,USER_ID) values ('account5@email',SHA('password5'), '5');
insert into account(ACCOUNT_EMAIL,ACCOUNT_PASSWORD,USER_ID) values ('account6@email',SHA('password6'), '6');
insert into account(ACCOUNT_EMAIL,ACCOUNT_PASSWORD,USER_ID) values ('account7@email',SHA('password7'), '7');
insert into account(ACCOUNT_EMAIL,ACCOUNT_PASSWORD,USER_ID) values ('account8@email',SHA('password8'), '8');
insert into account(ACCOUNT_EMAIL,ACCOUNT_PASSWORD,USER_ID) values ('account9@email',SHA('password9'), '9');
insert into account(ACCOUNT_EMAIL,ACCOUNT_PASSWORD,USER_ID) values ('account10@email',SHA('password10'), '10');


