package com.example.service;

//import javax.transaction.Transactional;

//import com.example.entity.Account;
//import com.example.entity.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
//import com.example.entity.Message;
import com.example.repository.AccountRepository;
//import com.example.repository.MessageRepository;

//import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() || account.getPassword().length() < 4)
        //return null;
        throw new IllegalArgumentException("Invalid username or password");
    return accountRepository.save(account);
    }
    @Transactional
    public boolean usernameExists(Account account){
        return accountRepository.existsById(account.getAccountId());
    }

    @Transactional
    public Optional<Account> login(Account account) {
        //return Optional.of(accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword()));
        Account found = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        return Optional.ofNullable(found);
    }

    @Transactional
    public boolean idExists(int id){
        return accountRepository.existsById(id);
    }
   
}
