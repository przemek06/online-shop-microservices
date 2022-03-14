package com.example.demo.consumer;

import com.example.demo.dto.TransactionDto;
import com.example.demo.service.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.function.Consumer;

@Component
public class NotificationConsumer {

    EmailService emailService;

    public NotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @Bean
    public Consumer<TransactionDto> onNotification() {
        return (message) -> {
            try {
                emailService.sendConfirmationToCustomer(message);
            } catch (MessagingException | IOException e) {
                e.printStackTrace();
            }
        };
    }
}
