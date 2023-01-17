package com.udupa.useraccoutapi.controller;

import com.udupa.useraccoutapi.exception.BadRequestException;
import com.udupa.useraccoutapi.model.Account;
import com.udupa.useraccoutapi.service.AccountService;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/api/v1/account")
public class AccountController {
    Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @PostMapping()
    public ResponseEntity createAccount(@RequestBody Account account) {
        try {
            account = accountService.createAccount(account);
        } catch (BadRequestException be) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(be.getMessage());
        } catch (Exception e) {
            log.error("Error occurred while creating account. " + Arrays.toString(e.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping(value = "/by-user-id")
    public ResponseEntity getAccountByEmail(@RequestParam(name = "user-id") Integer userId) {
        if(userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user Id: ");
        }
        try {
            return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountsByUserId(userId));
        } catch (BadRequestException be) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(be.getMessage());
        } catch (Exception e) {
            log.error("Error occurred while fetching accounts by user Id. " + Arrays.toString(e.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity getAllAccounts() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccounts());
        } catch (Exception e) {
            log.error("Error occurred while fetching all accounts. " + Arrays.toString(e.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
