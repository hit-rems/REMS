package com.fj.rems_backend.controller;

import com.fj.rems.pojo.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @GetMapping("/list")
    public Result<String> list(@RequestHeader(name = "Authorization") String token, HttpServletResponse response){
        return Result.success("hhh");
    }
}
