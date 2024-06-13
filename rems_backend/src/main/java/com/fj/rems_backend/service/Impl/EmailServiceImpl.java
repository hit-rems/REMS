package com.fj.rems_backend.service.Impl;

import com.fj.rems_backend.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void send(String to, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);  // 第二个参数true表示邮件内容为HTML格式
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // 处理发送邮件异常
        }
    }

    @Async
    @Override
    public void emailVerify(String email,String username) {
        String subject = "激活邮件";
        String text = "<p>点击以下链接激活账户：</p>"
                    + "<a href='http://localhost:8080/user/active?username=" + username + "'>"
                    + "激活链接</a>";
        send(email, subject, text);
    }
}
