package com.udupa.useraccoutapi.service;

import com.udupa.useraccoutapi.exception.BadRequestException;
import com.udupa.useraccoutapi.model.User;
import com.udupa.useraccoutapi.repository.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public User createUser(User user) throws Exception {
        log.info("In for creation");
        validateUserInput(user);
        user.setEmail(user.getEmail().trim());
        user = userRepository.save(user);
        return user;
    }

    public User getUserByEmail(String email) {
            return userRepository.findByEmail(email);
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private void validateUserInput(User user) throws BadRequestException {
        String errorMsg = null;
        if(user.getFirstName() == null) {
            errorMsg = "First name cannot be empty.";
        } else if(user.getLastName() == null) {
            errorMsg = "Last name cannot be empty.";
        } else if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            errorMsg = "Email address is not valid.";
        } else if(user.getMonthlyExpense() == null) {
            errorMsg = "Monthly expense cannot be empty.";
        }else if(user.getMonthlySalary() == null) {
            errorMsg = "Monthly salary cannot be empty.";
        } else if (userRepository.findByEmail(user.getEmail().trim()) != null){
            errorMsg = "There is an existing user with email address : " + user.getEmail();
        }
        if(errorMsg != null) {
            log.info(errorMsg);
            throw new BadRequestException(errorMsg);
        }
        log.info("Completed validation");
    }

}
