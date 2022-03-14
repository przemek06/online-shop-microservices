package com.example.demo.service;

import com.example.demo.dto.TransactionDto;
import com.example.demo.utils.HTMLUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class EmailService {
    @Value(value = "${local.manger.email}")
    String managerEmail;

    JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendConfirmationToCustomer(TransactionDto transactionDto) throws MessagingException, IOException {
        MimeMessage simpleMailMessage = javaMailSender.createMimeMessage();
        String html = HTMLUtils.getResourceFileAsString("CustomerConfirmationTemplate.html");
        html = String.format(html, transactionDto.getAddress(), transactionDto.getId(), managerEmail);
        simpleMailMessage.setSubject("Confirmation");
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(simpleMailMessage, true);
        helper.setTo(transactionDto.getOwnerEmail());
        helper.setText(html, true);
        javaMailSender.send(simpleMailMessage);
    }
}
