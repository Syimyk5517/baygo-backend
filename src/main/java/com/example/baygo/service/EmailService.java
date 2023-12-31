package com.example.baygo.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmail(String to, String subject, String body);
    void sendSellerConfirmationEmail(String email, String confirmationCode);
}
