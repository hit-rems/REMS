package com.fj.rems_backend.controller;

import com.fj.rems_backend.pojo.*;
import com.fj.rems_backend.service.AuditService;
import com.fj.rems_backend.utils.ThreadLocalUtil;
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
        Map<String, Object> mapInfo = ThreadLocalUtil.get();
        int id = (int) mapInfo.get("id");
        return Result.success(auditService.pagelist(map, id));
    }

    @PostMapping("/update")
    public Result update(@RequestBody Map<String,Object> map){
        auditService.update(map);
        return Result.success();
    }

    @PostMapping("/updatelist")
    public Result updatelist(@RequestBody Map<String,Object> map){
        auditService.updatelist(map);
        return Result.success();
    }
}
