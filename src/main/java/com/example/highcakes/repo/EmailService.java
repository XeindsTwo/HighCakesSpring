package com.example.highcakes.repo;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
