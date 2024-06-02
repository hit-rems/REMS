package com.fj.project_rems.service;

import com.fj.project_rems.pojo.User;


public interface UserService {

    User findByUserName(String username);

    void register(String username, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String new_pwd);
}
