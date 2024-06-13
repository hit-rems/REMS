package com.fj.rems_backend.service.Impl;

import com.fj.rems_backend.mapper.UserMapper;
import com.fj.rems_backend.pojo.User;
import com.fj.rems_backend.service.UserService;
import com.fj.rems_backend.utils.Md5Util;
import com.fj.rems_backend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailServiceImpl emailService;

    @Override
    public User findByUserName(String username) {
        User u=userMapper.findByUserName(username);
        return u;
    }

    @Override
    public User findByUserId(Integer id) {
        User u=userMapper.findByUserId(id);
        return u;
    }

    @Override
    public void register(User user) {
        //加密
        String md5String = Md5Util.getMD5String(user.getPassword());
        user.setPassword(md5String);
        user.setUrl("https://tse4-mm.cn.bing.net/th/id/OIP-C.NooTQCp-Aowgc_oFC7FsiQAAAA?rs=1&pid=ImgDetMain");
        user.setCode("0");//代表未激活
        userMapper.add(user);
        //发送邮件
        emailService.emailVerify(user.getEmail(),user.getUsername());
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String new_pwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(new_pwd),id);
    }

    @Override
    public void findPassword() {
//        Map<String,Object> map = ThreadLocalUtil.get();
//        Integer id = (Integer) map.get("id");
//        User u = userMapper.findByUserId(id);
//        //解密
//        emailService.send(u.getEmail(),"找回密码","您的密码是："+u.getPassword());
    }

    @Override
    public void active(String username) {
        userMapper.active(username);
    }
}
