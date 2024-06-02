package com.fj.project_heima.service;

import com.fj.project_heima.pojo.User;

import java.util.Map;


public interface UserService {

    User findByUserName(String username);

    void register(String username, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String new_pwd);
}
