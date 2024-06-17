package com.fj.rems_backend.service.Impl;

import com.fj.rems_backend.service.EmailService;
import com.fj.rems_backend.utils.Md5Util;
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
    @Value("${file.virtualPath}")
    private String virtualPath;

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
    public void emailVerify(String email,String username,String type) {
        String subject = "激活邮件";
        String text = "";
        if (type.equals("管理员")){
            text = "<p>点击以下链接请求超级管理员审核：</p>"
                    + "<a href='"+virtualPath+"/user/admin/active?username=" + username + "'>"
                    + "激活链接</a>";
        }else{
            text = "<p>点击以下链接激活账户：</p>"
                    + "<a href='"+virtualPath+"/user/active?username=" + username + "'>"
                    + "激活链接</a>";
        }
        send(email, subject, text);
    }

    @Async
    @Override
    public void initPassword(String email, String username) {
        String subject = "重置密码";
        String text = "<p>点击以下链接重置密码为123456：</p>"
                    + "<a href='"+virtualPath+"/user/resetPwd?username=" + username + "'>"
                    + "重置密码链接</a>";
        send(email, subject, text);
    }
}
