package com.example.service;

import com.example.entity.Account;
import com.example.exception.badFormatException;
import com.example.exception.duplicateUsernameException;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;


@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account register(Account account) throws duplicateUsernameException, badFormatException {
        accountRepository.findByUsername(account.getUsername())
                .ifPresent(foundAccount -> {
                    throw new duplicateUsernameException("Username already exists. Try again.");
                });
        if(account.getUsername().isEmpty() || account.getPassword().length() < 4) {
            throw new badFormatException("Username was not be blank and password must be at least 4 characters long");
        }

        return accountRepository.save(account);
    }

    public Account login(Account account) throws AuthException {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword())
                .orElseThrow(() -> new AuthException("Username or password wrong. Try again."));

    }
}
