package com.fj.rems_backend.service;

import com.fj.rems_backend.pojo.User;


public interface UserService {

    User findByUserName(String username);

    User findByUserId(Integer id);

    void register(User user);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String new_pwd);
}
