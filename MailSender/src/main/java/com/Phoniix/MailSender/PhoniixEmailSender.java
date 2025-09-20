package com.Phoniix.MailSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.Phoniix.MailSender.dto.EmailRequest;

@Service
public class PhoniixEmailSender {

    private static final Logger logger = LoggerFactory.getLogger(PhoniixEmailSender.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail (String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);

            mailSender.send(message);
            logger.info("Email sent successfully to: {}", toEmail);
        } catch (MailException e) {
            logger.error("Failed to send email to: {}. Error: {}", toEmail, e.getMessage());
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }

    public void sendEmail(EmailRequest emailRequest) {
        sendEmail(emailRequest.getToEmail(), emailRequest.getSubject(), emailRequest.getBody());
    }

}
