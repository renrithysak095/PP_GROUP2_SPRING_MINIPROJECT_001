package com.example.mini_project.service;

import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;

@Service
public interface EmailService {
    void sendMailMessage() throws MessagingException;
}
