package com.paymybuddy.PayMyBuddy;

import com.paymybuddy.PayMyBuddy.service.AccountService;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AccountService.class)
public class AccountServiceTest {
//
//    @MockBean
//    Connection connection;
//    @MockBean
//    IAccountRepository accountRepository;
//    @Autowired
//    IAccountService accountService;
//
//    Account account1 = new Account();
//    Account account2 = new Account();
//    List<Account> accountList = new ArrayList<>();
//    Account accountUpdate = new Account();
//    Account newAccount = new Account();
//    Account accountAuthenticate = new Account();
//
//    @BeforeEach
//    public void config() {
//
//        account1.setAccountID(1);
//        account1.setAccountEmail("account1Test@email");
//        account1.setAccountPassword("e38ad214943daad1d64c102faec29de4afe9da3d");
//        account1.setUserID(1);
//
//        account2.setAccountID(2);
//        account2.setAccountEmail("account2Test@email");
//        account2.setAccountPassword("e38ad214943daad1d64c102faec29de4afe9da3d");
//        account2.setUserID(2);
//
//        accountList.add(account1);
//        accountList.add(account2);
//
//        when(accountRepository.readAccountList()).thenReturn(accountList);
//        when(accountRepository.readAccount(1)).thenReturn(account1);
//        when(accountRepository.readUsersAccount(1)).thenReturn(account1);
//
//        accountUpdate = account1;
//        accountUpdate.setAccountEmail("updateEmail");
//        when(accountRepository.updateAccount(accountUpdate)).thenReturn(true);
//        when(accountRepository.alreadyExist("inventedAccount@email")).thenReturn(false);
//        when(accountRepository.alreadyExist("account2Test@email")).thenReturn(true);
//
//        newAccount.setAccountEmail("newAccountEmail");
//        newAccount.setAccountPassword("newAccountPassword");
//        newAccount.setUserID(3);
//        when(accountRepository.createAccount(connection, newAccount)).thenReturn(true);
//
//        accountAuthenticate.setAccountEmail("account2Test");
//        accountAuthenticate.setAccountPassword("password2");
//        when(accountRepository.authenticate(accountAuthenticate)).thenReturn(account2);
//    }
//
//    @Test
//    public void AccountCrudTest() {
//        assertThat(accountService.createAccount(connection,newAccount)).isEqualTo(true);
//        assertThat(accountService.readAccount(1)).isEqualTo(account1);
//        assertThat(accountService.readUsersAccount(1)).isEqualTo(account1);
//        assertThat(accountService.readAccountList()).isEqualTo(accountList);
//        HashMap<String ,Object>params=new HashMap<>();
//        params.put("accountEmail","updateEmail");
//        assertThat(accountService.updateAccount(1,params)).isEqualTo(true);
//    }
//    @Test
//    public void alreadyExistTest(){
//        assertThat(accountService.alreadyExist("inventedAccount@email")).isEqualTo(false);
//        assertThat(accountService.alreadyExist("account2Test@email")).isEqualTo(true);
//    }
//
//    @Test
//    public void authenticateTest(){
//        assertThat(accountService.authenticate(accountAuthenticate)).isEqualTo(account2);
//    }
}
