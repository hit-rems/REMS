package com.fj.rems_backend.controller;

import com.fj.rems_backend.pojo.*;
import com.fj.rems_backend.service.AuditService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/audit")
public class AuditController {
    @Autowired
    private AuditService auditService;

    @PostMapping("/pagelist")
    public Result<PageBeanAudit<Audit>> list(@RequestBody Map<String,Object> map){
        return Result.success(auditService.pagelist(map));
    }
}
