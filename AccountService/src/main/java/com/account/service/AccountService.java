package com.account.service;

import com.account.dto.LoginDto;
import com.account.dto.TransactionDto;
import com.account.entity.Account;
import com.account.exceptions.InsufficientBalanceException;

import java.util.List;

public interface AccountService {

    public Account createAccount(Account account);

    public Account updateAccount(Account account,int accid);

    public TransactionDto addTransaction(TransactionDto  transactionDto);

    public List<Account> getAllAccounts();

    public Account findAccountById(int accid);

    public String deleteAccount(int accid);

    public String activateAccount(int accid);

    public String deactivateAccount(int accid);

   public Account loginAccount(LoginDto cred) ;
}
