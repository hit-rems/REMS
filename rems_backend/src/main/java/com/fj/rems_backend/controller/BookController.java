package com.fj.rems_backend.controller;

import com.fj.rems_backend.pojo.Audit;
import com.fj.rems_backend.pojo.PageBeanAudit;
import com.fj.rems_backend.pojo.Result;
import com.fj.rems_backend.service.AuditService;
import com.fj.rems_backend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fj.rems_backend.service.BookService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private AuditService auditService;

    @PostMapping("/add")
    public Result add(@RequestBody Map<String, Object> map) {
        bookService.add(map);
        return Result.success();
    }

    @PostMapping("query")
    public Result<List<Boolean>> query(@RequestBody Map<String, Object> map) {
        return Result.success(bookService.query(map));
    }

    @PostMapping("userInfo")
    public Result<PageBeanAudit<Audit>> userInfo(@RequestBody Map<String,Object> map) {
        Map<String, Object> mapInfo = ThreadLocalUtil.get();
        int id = (int) mapInfo.get("id");
        map.put("role", "学生");
        return Result.success(auditService.pagelist(map, id));
    }

}
