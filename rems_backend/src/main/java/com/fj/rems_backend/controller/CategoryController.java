package com.fj.rems_backend.controller;

import com.fj.rems_backend.pojo.Result;
import com.fj.rems_backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //获取设备的所有分类信息
    @GetMapping("/list")
    public Result<List<Map<String,Integer>>> list(){
        return Result.success(categoryService.list());
    }

    //添加，path传参
    @GetMapping("/add/{name}")
    public Result addOne(@PathVariable String name){
        categoryService.addCategory(name);
        return Result.success();

    }

}
