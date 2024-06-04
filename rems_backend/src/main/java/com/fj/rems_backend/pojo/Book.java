package com.fj.rems_backend.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Book {
    private Integer userId;
    private Integer equipmentId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String reason;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
