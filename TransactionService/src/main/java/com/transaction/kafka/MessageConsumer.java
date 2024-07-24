package com.transaction.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transaction.dto.TransactionDto;
import com.transaction.entity.Transaction;
import com.transaction.entity.User;
import com.transaction.repository.TransactionRepository;
import com.transaction.repository.UserRepository;
import com.transaction.util.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MessageConsumer {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "my-topic", groupId = "my-group-id")
    public void listen(String message) {
        try {
            // Deserialize the message
            TransactionDto transactionDto = objectMapper.readValue(message, TransactionDto.class);

            Optional<User> usera = userRepository.findByAccountNumber(transactionDto.getAccountNumber());
            if (usera.isEmpty()) {
                User user = new User();
                user.setUserName(transactionDto.getUserName());
                user.setAccountNumber(transactionDto.getAccountNumber());
                user.setUserEmail(transactionDto.getUserEmail());
                user.setUserCreatedDate(transactionDto.getUserCreatedDate());
                userRepository.save(user);
            }
            Optional<User> user1 = userRepository.findByAccountNumber(transactionDto.getAccountNumber());

            Transaction transaction = new Transaction();
            transaction.setDescription(transactionDto.getDescription());
            transaction.setAmount(transactionDto.getAmount());
            transaction.setUser(user1.get());
            transaction.setCreatedDate(LocalDate.now());
            transaction.setCredited(true);

            // Save to database
            Transaction save = transactionRepository.save(transaction);
            emailService.sendTemplateEmail(save);
            System.out.println("Transaction saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
