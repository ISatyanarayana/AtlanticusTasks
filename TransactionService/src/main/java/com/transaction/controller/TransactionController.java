package com.transaction.controller;

import com.transaction.entity.Transaction;
import com.transaction.entity.User;
import com.transaction.service.TransactionService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    public TransactionService transactionService;
    @GetMapping("/getTransactions")
    public List<Transaction> getTransactions(){
        return  transactionService.getTransactions();
    }

    @GetMapping("/getTransaction/{id}")
    public Transaction getTransactionById(@PathVariable int id){
        return  transactionService.getTransactionById(id);
    }
}
