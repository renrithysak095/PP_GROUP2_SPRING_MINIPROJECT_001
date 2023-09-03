package com.example.mini_project.service.implementation;

import com.example.mini_project.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    private final TemplateEngine emailTemplateEngine;
    private final JavaMailSender emailSender;

    private static final String TEMPLATE = "message";

    public EmailServiceImpl(TemplateEngine emailTemplateEngine, JavaMailSender emailSender) {
        this.emailTemplateEngine = emailTemplateEngine;
        this.emailSender = emailSender;
    }

    @Override
    public void sendMailMessage() throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        boolean html = true;
        Context thymeleafContext = new Context(LocaleContextHolder.getLocale());
        final String emailContent = this.emailTemplateEngine.process(TEMPLATE, thymeleafContext);
        messageHelper.setTo("renrithysak095@gmail.com");
        messageHelper.setSubject("Email Sending");
        messageHelper.setText(emailContent, html);
        emailSender.send(mimeMessage);
    }
}
