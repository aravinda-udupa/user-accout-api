package com.udupa.useraccoutapi.service;

import com.udupa.useraccoutapi.exception.BadRequestException;
import com.udupa.useraccoutapi.model.Account;
import com.udupa.useraccoutapi.model.User;
import com.udupa.useraccoutapi.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(MockitoJUnitRunner.class)
class AccountServiceTest {
    
    @InjectMocks
    AccountService accountService;
    
    @Mock
    AccountRepository accountRepository;

    @Mock
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccountInvalidAccountType() {
        Account account = new Account();
        account.setType("ABC");
        BadRequestException bre =assertThrows(BadRequestException.class, () -> {
            accountService.createAccount(account);
        });
        String expectedMsg = "Invalid account type.";
        assertEquals(expectedMsg, bre.getMessage());
    }

    @Test
    void createAccountWithSalaryExpenseMismatch() {
        User user = new User();
        user.setFirstName("ABC");
        user.setLastName("DEF");
        user.setEmail("abc@def.com");
        user.setMonthlyExpense(1500.00);
        user.setMonthlySalary(1000.00);
        Mockito.when(userService.getUserById(123)).thenReturn(user);
        Account account = new Account();
        account.setUserId(123);
        account.setType("SAVINGS");
        BadRequestException bre =assertThrows(BadRequestException.class, () -> {
            accountService.createAccount(account);
        });
        String expectedMsg = "Monthly salary and expense difference cannot be less than $1000.0";
        assertEquals(expectedMsg, bre.getMessage());
    }


    @Test
    void createAccountWithNoUser() {
        Account account = new Account();
        account.setType("SAVINGS");
        account.setUserId(123);
        Mockito.when(userService.getUserById(123)).thenReturn(null);
        BadRequestException bre =assertThrows(BadRequestException.class, () -> {
            accountService.createAccount(account);
        });
        String expectedMsg = "User not found for : 123";
        assertEquals(expectedMsg, bre.getMessage());
    }

    @Test
    void getAccountByUserIdWithNoAccounts() throws BadRequestException {
        Mockito.when(accountRepository.findByUserId(123)).thenReturn(new ArrayList<>());
        BadRequestException bre = assertThrows(BadRequestException.class, () -> {
            accountService.getAccountsByUserId(123);
        });
        String expectedMsg = "Couldn't find any accounts for user Id 123";
        assertEquals(expectedMsg, bre.getMessage());
    }
    
    @Test
    void getAccountByUserId() throws BadRequestException {
        Account account = new Account();
        account.setUserId(123);
        account.setId(1);
        account.setType("SAVINGS");
        account.setBalance(1500.00);
        account.setInterestRate(4.50);
        Mockito.when(accountRepository.findByUserId(123)).thenReturn(Arrays.asList(account));
        assertEquals(1, accountService.getAccountsByUserId(123).size());
    }

    @Test
    void getAllAccounts() {
        Account account = new Account();
        account.setId(1);
        account.setType("SAVINGS");
        account.setBalance(1500.00);
        account.setInterestRate(4.50);

        Account account1 = new Account();
        account1.setId(2);
        account.setType("LOAN");
        account.setBalance(1500.00);
        account.setInterestRate(8.50);
        Mockito.when(accountRepository.findAll()).thenReturn(Arrays.asList(account, account1));
        assertEquals(2, accountService.getAllAccounts().size());
    }
}