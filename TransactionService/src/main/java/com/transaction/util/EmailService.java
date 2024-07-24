package com.transaction.util;

import com.transaction.entity.Transaction;
import com.transaction.entity.User;
import com.transaction.repository.TransactionRepository;
import com.transaction.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private freemarker.template.Configuration freemarkerConfig;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendTemplateEmail(Transaction transaction) throws MessagingException, IOException, TemplateException {

        User user = userRepository.findById(transaction.getUser().getUserId()).get();

        String email = user.getUserEmail();
        String username = user.getUserName();

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("name", username);
        dataModel.put("transaction", transaction);

        logger.info("Data model for email template: {}", dataModel);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Template template = freemarkerConfig.getTemplate("template.ftlh");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataModel);

        helper.setTo(email);
        helper.setSubject("Transaction Details");
        helper.setText(html, true);

        javaMailSender.send(message);
    }
}
