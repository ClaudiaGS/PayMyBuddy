package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.service.interfaces.IOperationsService;
import org.springframework.stereotype.Service;

@Service
public class OperationsService implements IOperationsService {
//    @Autowired
//    OperationsRepository operationsRepository;
//    @Autowired
//    AccountService accountService;
//    @Autowired
//    TransactionService transactionService;
//    @Autowired
//    BankAccountService bankAccountService;
//
//    private static final Logger logger = LogManager.getLogger("OperationService");
//
//    @Override
//    public void createAccount(Account account) {
//
//
//    }
//
//    @Override
//    public void registerAccount(Account account) {
//
//    }
//
//    @Override
//    public boolean authentificate(String email, String password) {
//        boolean authentificationSucceeded=false;
//        String actualPassword=accountService.readAccountEmailBased(email).getAccountPassword();
//        if(actualPassword.equals(operationsRepository.getPasswordEncoded(password))){
//            authentificationSucceeded=true;
//        }
//        else authentificationSucceeded=false;
//        logger.info("authetification = "+authentificationSucceeded);
//        return authentificationSucceeded;
//    }
//    @Override
//    public double processSendMoney(String transactionDescription, double transactionDebitedAmount, int userIDSender, int userIDReceiver){
//        Transaction transaction=transactionService.createTransaction(transactionDescription,transactionDebitedAmount,userIDSender,userIDReceiver);
//        BankAccount bankAccount= new BankAccount();
//        bankAccount.setUserID(userIDSender);
//        List<BankAccount> bankAccountList=new ArrayList<BankAccount>();
//        bankAccountList=bankAccountService.readBankAccountList();
//
//
//        bankAccountService.updateBankAccount()
//    }
//
    }
