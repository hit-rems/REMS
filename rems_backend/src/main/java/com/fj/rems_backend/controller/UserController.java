package com.fj.rems_backend.controller;

import com.fj.rems_backend.pojo.Result;
import com.fj.rems_backend.pojo.User;
import com.fj.rems_backend.service.EmailService;
import com.fj.rems_backend.service.FileUploadService;
import com.fj.rems_backend.service.UserService;
import com.fj.rems_backend.utils.JwtUtil;
import com.fj.rems_backend.utils.Md5Util;
import com.fj.rems_backend.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private EmailService emailService;
    @Value("${frontend.frontendAccessPath}")
    private String frontendAccessPath;

    @PostMapping("/register")
    public Result register(User user){
        //查询用户
        User u =userService.findByUserName(user.getUsername());
        if (u==null){
            //注册
            userService.register(user);
            return Result.success();
        } else if (u.getCode().equals("1")){
            return Result.error("用户名已经被占用");
        } else {
            emailService.emailVerify(user.getEmail(),user.getUsername());
            return Result.success();
        }
    }

    @PostMapping("/login")
    public Result<String> login(String username,String password){
        User u=userService.findByUserName(username);
        if (u==null){
            return Result.error("用户名错误");
        }else if (u.getCode().equals("0")){
            return Result.error("用户未激活");
        } else {
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

    @GetMapping("/active")
    public void verify(String username, HttpServletResponse response) throws IOException {
        userService.active(username);
        //跳转到登录页面
        response.sendRedirect(frontendAccessPath+"/login");
    }

    @GetMapping("/userInfo")
    public  Result<User> userinfo(){
        Map<String,Object> clians = ThreadLocalUtil.get();
        User user = userService.findByUserId((Integer) clians.get("id"));
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody User user){
        Map<String,Object> clians = ThreadLocalUtil.get();
        user.setId((Integer) clians.get("id"));
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        String old_pwd=params.get("old_pwd");
        String new_pwd=params.get("new_pwd");
        Map<String,Object> map = ThreadLocalUtil.get();
        String name = (String) map.get("username");
        User user = userService.findByUserName(name);
        if (!user.getPassword().equals(Md5Util.getMD5String(old_pwd))){
            return Result.error("密码错误");
        }
        userService.updatePwd(new_pwd);
        return Result.success();
    }

    @PostMapping("/updateAvatar")
    public Result updateAvatar(MultipartFile file){
        String url = fileUploadService.uploadFile(file);
        userService.updateAvatar(url);
        return Result.success(url);
    }

    @GetMapping("/findPassword")
    public Result findPassword(){
        userService.findPassword();
        return Result.success();
    }

}
