package com.udupa.useraccoutapi.service;

import com.udupa.useraccoutapi.exception.BadRequestException;
import com.udupa.useraccoutapi.model.Account;
import com.udupa.useraccoutapi.model.User;
import com.udupa.useraccoutapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserWithFirstNameNull() {
        User user = new User();
        BadRequestException bre =assertThrows(BadRequestException.class, () -> {
            userService.createUser(user);
        });
        String expectedMsg = "First name cannot be empty.";
        assertEquals(expectedMsg, bre.getMessage());
    }

    @Test
    void createUserWithLastNameNull() {
        User user = new User();
        user.setFirstName("ABC");
        BadRequestException bre =assertThrows(BadRequestException.class, () -> {
            userService.createUser(user);
        });
        String expectedMsg = "Last name cannot be empty.";
        assertEquals(expectedMsg, bre.getMessage());
    }

    @Test
    void createUserWithInvalidEmail() {
        User user = new User();
        user.setFirstName("ABC");
        user.setLastName("DEF");
        BadRequestException bre =assertThrows(BadRequestException.class, () -> {
            userService.createUser(user);
        });
        String expectedMsg = "Email address is not valid.";
        assertEquals(expectedMsg, bre.getMessage());
    }

    @Test
    void createUserWithExistingEmail() {
        User user = new User();
        user.setFirstName("ABC");
        user.setLastName("DEF");
        user.setMonthlyExpense(1500.00);
        user.setMonthlySalary(500.00);
        user.setEmail("abc@def.com");
        Mockito.when(userRepository.findByEmail("abc@def.com")).thenReturn(new User());
        BadRequestException bre =assertThrows(BadRequestException.class, () -> {
            userService.createUser(user);
        });
        String expectedMsg = "There is an existing user with email address : abc@def.com";
        assertEquals(expectedMsg, bre.getMessage());
    }

    @Test
    void createUserWithMonthlyExpense() {
        User user = new User();
        user.setFirstName("ABC");
        user.setLastName("DEF");
        user.setEmail("abc@def.com");
        BadRequestException bre =assertThrows(BadRequestException.class, () -> {
            userService.createUser(user);
        });
        String expectedMsg = "Monthly expense cannot be empty.";
        assertEquals(expectedMsg, bre.getMessage());
    }

    @Test
    void createAccountWithMonthlySalary() {
        User user = new User();
        user.setFirstName("ABC");
        user.setLastName("DEF");
        user.setEmail("abc@def.com");
        user.setMonthlyExpense(1500.00);
        BadRequestException bre =assertThrows(BadRequestException.class, () -> {
            userService.createUser(user);
        });
        String expectedMsg = "Monthly salary cannot be empty.";
        assertEquals(expectedMsg, bre.getMessage());
    }

    @Test
    void createUserWithSuccess() throws Exception {
        User user = new User();
        user.setFirstName("ABC");
        user.setLastName("DEF");
        user.setEmail("abc@def.com");
        user.setMonthlyExpense(1500.00);
        user.setMonthlySalary(500.00);
        User savedUser = new User();
        savedUser.setId(1);
        Mockito.when(userRepository.save(user)).thenReturn(savedUser);
        user = userService.createUser(user);
        assertEquals(1, user.getId());
    }

    @Test
    void getUserByEmail() {
        User user = new User();
        user.setId(1);
        user.setFirstName("ABC");
        user.setLastName("DEF");
        user.setEmail("abc@def.com");
        Mockito.when(userRepository.findByEmail("abc@def.com")).thenReturn(user);
        User user1 = userService.getUserByEmail("abc@def.com");
        assertEquals(user1.getId(), user.getId());
    }

    @Test
    void getAllUsers() {
        User user = new User();
        user.setId(1);
        user.setFirstName("ABC");
        user.setLastName("DEF");
        user.setEmail("abc@def.com");

        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("ABC");
        user1.setLastName("DEF");
        user1.setEmail("abc@def.com");
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user, user1));
        assertEquals(2, userService.getAllUsers().size());
    }
}