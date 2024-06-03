package com.fj.rems_backend.service;

import com.fj.rems.pojo.User;


public interface UserService {

    User findByUserName(String username);

    void register(String username, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String new_pwd);
}
