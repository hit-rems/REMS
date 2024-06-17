package com.fj.rems_backend.controller;


import com.fj.rems_backend.pojo.*;
import com.fj.rems_backend.service.SuperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/super")
public class SuperController {
    @Autowired
    private SuperService superService;
    @PostMapping("/pagelist")
    public Result<PageBeanAudit<Admin>> pagelist(@RequestBody Map<String,Object> map){
        PageBeanAudit<Admin> AdminPageBean=superService.pagelist(map);
        return Result.success(AdminPageBean);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Map<String,Object> map){
        superService.update(map);
        return Result.success();
    }
}
