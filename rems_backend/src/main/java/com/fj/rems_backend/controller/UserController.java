package com.fj.rems_backend.controller;

import com.fj.rems_backend.pojo.Result;
import com.fj.rems_backend.pojo.User;
import com.fj.rems_backend.service.UserService;
import com.fj.rems_backend.utils.JwtUtil;
import com.fj.rems_backend.utils.Md5Util;
import com.fj.rems_backend.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(User user){
        //查询用户
        User u =userService.findByUserName(user.getUsername());
        if (u==null){
            //注册
            userService.register(user);
            return Result.success();
        }else {
            return Result.error("用户名已经被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(String username,String password){
        User u=userService.findByUserName(username);
        if (u==null){
            return Result.error("用户名错误");
        }else {
            //判断密码
            if (Md5Util.getMD5String(password).equals(u.getPassword())){
                HashMap<String, Object> clians = new HashMap<>();
                clians.put("id",u.getId());
                clians.put("username",username);
                String jwt = JwtUtil.genToken(clians);
                return Result.success(jwt);
            }else {
                return Result.error("密码错误");
            }
        }
    }

    @GetMapping("/userInfo")
    public  Result<User> userinfo(){
        Map<String,Object> clians = ThreadLocalUtil.get();
        User user = userService.findByUserName((String) clians.get("username"));
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam(name = "avatarUrl") @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        String old_pwd=params.get("old_pwd");
        String new_pwd=params.get("new_pwd");
        String re_pwd=params.get("re_pwd");
        if (!StringUtils.hasLength(old_pwd)||!StringUtils.hasLength(new_pwd)||!StringUtils.hasLength(re_pwd)){
            return Result.error("缺少参数");
        }
        Map<String,Object> map = ThreadLocalUtil.get();
        String name = (String) map.get("username");
        User user = userService.findByUserName(name);
        if (!user.getPassword().equals(Md5Util.getMD5String(old_pwd))){
            return Result.error("密码错误");
        }
        if (!new_pwd.equals(re_pwd)){
            return Result.error("新旧密码不一致");
        }
        userService.updatePwd(new_pwd);
        return Result.success();
    }
}
