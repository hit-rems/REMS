package com.fj.rems_backend.service;

public interface EmailService {
    void send(String to, String subject, String text);

    void emailVerify(String email,String username,String type);

    void initPassword(String email,String username);
}
