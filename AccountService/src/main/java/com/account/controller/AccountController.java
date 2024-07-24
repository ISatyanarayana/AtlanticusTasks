package com.account.controller;

import com.account.dto.LoginDto;
import com.account.dto.LoginResponseDto;
import com.account.dto.TransactionDto;
import com.account.entity.Account;
import com.account.exceptions.AccountAlreadyExistsException;
import com.account.jwt.JwtService;
import com.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService  accountService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login" )
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto cred) {
        Account authenticatedUser = accountService.loginAccount(cred);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDto loginResp = new LoginResponseDto(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.status(HttpStatus.OK).body(loginResp);
    }
    @PostMapping("/addTransaction" )
    public TransactionDto addTransaction(@RequestBody TransactionDto  transactionDto){
       return accountService.addTransaction(transactionDto);
    }
    @PostMapping("/addAccount")
    public ResponseEntity<Account> createAccount(@RequestBody  Account account)
    {
            return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);
    }

    @PutMapping("/updateAccount/{accid}")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account,@PathVariable int accid){

        return new ResponseEntity<>(accountService.updateAccount(account,accid),HttpStatus.ACCEPTED);
    }

    @GetMapping("/allAccounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(),HttpStatus.CREATED);
    }

    @GetMapping("/accountById")
    public ResponseEntity<Account> findAccountById(@RequestParam int accid){

        return new ResponseEntity<>(accountService.findAccountById(accid),HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount(@RequestParam int accid) {

        return new ResponseEntity<>(accountService.deleteAccount(accid),HttpStatus.CREATED);
    }

    @PutMapping("/activate/{accid}")
    public ResponseEntity<String> activateAccount(@PathVariable int accid) {

        return new ResponseEntity<>(accountService.activateAccount(accid),HttpStatus.CREATED);
    }

    @PutMapping("/deactivate/{accid}")
    public ResponseEntity<String> deactivateAccount(@PathVariable int accid) {

        return new ResponseEntity<>(accountService.deactivateAccount(accid),HttpStatus.CREATED);
    }
}
