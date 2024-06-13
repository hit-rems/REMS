package com.fj.rems_backend.service.Impl;

import com.fj.rems_backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void send(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public void emailVerify(String email,String username) {
        String subject = "激活邮件";
        String text = "点击'http://localhost:8080/user/active?username="+username+"'激活";
        send(email, subject, text);
    }
}
