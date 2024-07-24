package com.transaction.service;

import com.transaction.entity.Transaction;
import com.transaction.entity.User;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface TransactionService{

    public List<Transaction> getTransactions();

    public Transaction getTransactionById(int id);
}
