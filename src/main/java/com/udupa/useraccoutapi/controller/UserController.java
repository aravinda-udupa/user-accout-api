package com.udupa.useraccoutapi.controller;

import com.udupa.useraccoutapi.exception.BadRequestException;
import com.udupa.useraccoutapi.model.User;
import com.udupa.useraccoutapi.service.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping()
    public ResponseEntity createUser(@RequestBody User user) {
         try {
             user = userService.createUser(user);
         } catch (BadRequestException be) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(be.getMessage());
         } catch (Exception e) {
             log.error("Error occurred while creating user. " + Arrays.toString(e.getStackTrace()));
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
         }
         return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping(value = "/by-email")
    public ResponseEntity getUserByEmail(@RequestParam(name = "email") String email) {
        if(!EmailValidator.getInstance().isValid(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address: " + email);
        }
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmail(email));
        } catch (Exception e) {
            log.error("Error occurred while fetching user by email. " + Arrays.toString(e.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity getAllUsers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
        } catch (Exception e) {
            log.error("Error occurred while fetching all users. " + Arrays.toString(e.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
