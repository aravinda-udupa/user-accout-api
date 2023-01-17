package com.udupa.useraccoutapi.service;

import com.udupa.useraccoutapi.Utils;
import com.udupa.useraccoutapi.exception.BadRequestException;
import com.udupa.useraccoutapi.model.Account;
import com.udupa.useraccoutapi.model.User;
import com.udupa.useraccoutapi.repository.AccountRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.udupa.useraccoutapi.Utils.MONTHLY_CREDIT;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserService userService;

    Logger log = LoggerFactory.getLogger(AccountService.class);



    public Account createAccount( Account account) throws BadRequestException {
        validateAccountInput(account);
        updateAccountDefaults(account);
        account = accountRepository.save(account);
        return account;
    }

    private void updateAccountDefaults(Account account) {
        if(account.getBalance() == null) {
            account.setBalance(0.0);
        }
        account.setInterestRate(Utils.ACCOUNT_TYPE_TO_INTEREST_RATE_MAP.get(account.getType()));
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    private void validateAccountInput(Account account) throws BadRequestException {
        String errorMsg = null;
        if(account.getType() == null || !Utils.ACCOUNT_TYPE_TO_INTEREST_RATE_MAP.containsKey(account.getType())) {
            errorMsg = "Invalid account type.";
        } else {
            User user = userService.getUserById(account.getUserId());
            if (user == null) {
                errorMsg = "User not found for : " + account.getUserId();
            } else {
                // Check if monthly salary - monthly expense is less than Zip monthly credit.
                if ((user.getMonthlySalary() - user.getMonthlyExpense()) < MONTHLY_CREDIT) {
                    errorMsg = "Monthly salary and expense difference cannot be less than $" + MONTHLY_CREDIT;
                }
            }
        }
        if(errorMsg != null) {
            log.info(errorMsg);
            throw new BadRequestException(errorMsg);
        }
    }

    public List<Account> getAccountsByUserId(Integer userId) throws BadRequestException {
        List<Account> accounts = accountRepository.findByUserId(userId);
        if(accounts.isEmpty()) {
            throw new BadRequestException("Couldn't find any accounts for user Id " + userId);
        }
        return accounts;
    }
}
