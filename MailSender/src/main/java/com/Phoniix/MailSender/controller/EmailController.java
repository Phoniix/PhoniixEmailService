package com.Phoniix.MailSender.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Phoniix.MailSender.PhoniixEmailSender;
import com.Phoniix.MailSender.dto.EmailRequest;
import com.Phoniix.MailSender.dto.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    // Variable Declarations -----------------------------------------------------------------------
    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private PhoniixEmailSender emailSenderService;

    // Send Method ---------------------------------------------------------------------------------
    @PostMapping("/send")
    public ResponseEntity<EmailResponse> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            logger.info("Recieved email request: {}", emailRequest);

            emailSenderService.sendEmail(emailRequest);

            EmailResponse response = EmailResponse.success("Email sent successfully to " + emailRequest.getToEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error Sending email: {}", e.getMessage());
            EmailResponse response = EmailResponse.error("Failed to send email: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Health Check Method --------------------------------------------------------------------------
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "MailSender API");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }
    
}
