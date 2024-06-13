package com.fj.rems_backend.controller;

import com.fj.rems_backend.pojo.Audit;
import com.fj.rems_backend.pojo.Result;
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

    @PostMapping("/add")
    public Result add(@RequestBody Map<String, Object> map) {
        bookService.add(map);
        return Result.success();
    }

    @PostMapping("query")
    public Result<List<Boolean>> query(@RequestBody Map<String, Object> map) {
        return Result.success(bookService.query(map));
    }

    @GetMapping("userInfo")
    public Result<List<Audit>> userInfo() {
        return Result.success(bookService.userInfo());
    }

}
