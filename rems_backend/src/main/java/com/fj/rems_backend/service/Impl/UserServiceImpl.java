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

    @Override
    public User findByUserName(String username) {
        User u=userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(User user) {
        //加密
        String md5String = Md5Util.getMD5String(user.getPassword());
        user.setPassword(md5String);
        user.setUrl("https://tse4-mm.cn.bing.net/th/id/OIP-C.NooTQCp-Aowgc_oFC7FsiQAAAA?rs=1&pid=ImgDetMain");
        //insert
        userMapper.add(user);
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
}
