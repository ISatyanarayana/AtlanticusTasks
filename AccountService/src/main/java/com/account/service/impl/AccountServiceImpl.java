package com.account.service.impl;

import com.account.dto.LoginDto;
import com.account.dto.TransactionDto;
import com.account.entity.Account;
import com.account.exceptions.AccountAlreadyExistsException;
import com.account.exceptions.AccountNotFoundException;
import com.account.exceptions.InsufficientBalanceException;
//import com.account.kafka.MessageProducer;
import com.account.kafka.MessageProducer;
import com.account.repository.AccountRepository;
import com.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MessageProducer messageProducer;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Account loginAccount(LoginDto cred){
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(cred.getUserName(), cred.getPassword()));

        return accountRepository.findByEmail(cred.getUserName())
                .orElseThrow(() -> new AccountNotFoundException("Account Not found" + cred.getUserName()));
    }
    @Override
    public Account createAccount(Account account){
        Optional<Account> newAccount = accountRepository.findById(account.getAccountId());
        if(newAccount.isEmpty()) {
            account.setCreatedDate(LocalDate.now());
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            return accountRepository.save(account);
        }
        else{
            throw new AccountAlreadyExistsException("Account already exists with id "+account.getAccountId());

        }
    }

    @Override
    public Account updateAccount(Account account, int accid){
        Account account1 = accountRepository.findById(account.getAccountId()).orElseThrow
                (() -> new AccountAlreadyExistsException("Account doesnt exists with id"
                        + account.getAccountId()));

        account1.setAccountId(account.getAccountId());
        account1.setAccountNumber(account.getAccountNumber());
        account1.setAccountName(account.getAccountName());
        account1.setAccountType(account.getAccountType());
        account1.setBalance(account.getBalance());
        account1.setBankName(account.getBankName());
        account1.setIfsc(account.getIfsc());

        return accountRepository.save(account);
    }

    @Override
    public TransactionDto addTransaction(TransactionDto transactionDto) {
        int accountNumber = transactionDto.getAccountNumber();
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(
                () -> new AccountNotFoundException("Account not found with Account Number" + accountNumber)
        );
        if(transactionDto.getAmount()<account.getBalance()){
            account.setBalance(account.getBalance()-transactionDto.getAmount());
            accountRepository.save(account);
            logger.info("amount object saved");
            transactionDto.setUserEmail(account.getEmail());
            transactionDto.setUserName(account.getAccountName());
            transactionDto.setUserCreatedDate(account.getCreatedDate());
            transactionDto.setSucess(true);
            messageProducer.sendMessage("my-topic",transactionDto);
            return transactionDto;
        }
        throw new InsufficientBalanceException("You dont have sufficient balance." +
                "Your current balance:-"+account.getBalance());
    }

    @Cacheable(value = "Account")
    @Override
    public List<Account> getAllAccounts() {
        logger.info("Fetching all accounts");
        return accountRepository.findAll();
    }

    @Cacheable(value = "Account",key="#accid")
    @Override
    public Account findAccountById(int accid){
        logger.info("Fetching account with id: " + accid);
        Account account = accountRepository.findById(accid).orElseThrow
                (() -> new AccountAlreadyExistsException("Account doesnt exists with id"
                        + accid));
        return account;
    }

    @Override
    public String deleteAccount(int accid){
        Account account = accountRepository.findById(accid).orElseThrow
                (() -> new AccountAlreadyExistsException("Account doesnt exists with id"
                        + accid));
        accountRepository.deleteById(account.getAccountId());

        return "Account sucessfully deleted with id:"+account.getAccountId();
    }

    @Override
    public String activateAccount(int accid){
        Account account = accountRepository.findById(accid).orElseThrow
                (() -> new AccountAlreadyExistsException("Account doesnt exists with id"
                        + accid));
        account.setActive(true);
        accountRepository.save(account);
        return  "Account activated sucessfully!!"+account.getAccountId();
    }

    @Override
    public String deactivateAccount(int accid) {
        Account account = accountRepository.findById(accid).orElseThrow
                (() -> new AccountAlreadyExistsException("Account doesnt exists with id"
                        + accid));
        account.setActive(false);

        accountRepository.save(account);
        return  "Account deactivated sucessfully!!"+account.getAccountId();
    }
}
