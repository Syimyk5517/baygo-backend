package com.example.baygo.db.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
