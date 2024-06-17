package com.fj.rems_backend.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class Admin {
    private Integer id;//主键ID
    private String username;//用户名
    private String nickname;//昵称
    private String department;//部门
    private String email;//邮箱
    private String status;//状态
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
}
