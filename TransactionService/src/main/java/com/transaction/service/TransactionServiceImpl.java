package com.transaction.service;

import com.transaction.entity.Transaction;
import com.transaction.entity.User;
import com.transaction.repository.TransactionRepository;
import com.transaction.repository.UserRepository;

import com.transaction.util.EmailService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements  TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(int id) {
        return  transactionRepository.findById(id).get();
    }

}
