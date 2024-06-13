package com.fj.rems_backend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public class User {
    private Integer id;//主键ID
    private String username;//用户名
    @JsonIgnore //不返回给前端
    private String password;//密码
    private String nickname;//昵称
    private String department;//部门
    private String type;//类型
    private String url;//头像
    private String email;//邮箱
    private String code;//激活码
}
