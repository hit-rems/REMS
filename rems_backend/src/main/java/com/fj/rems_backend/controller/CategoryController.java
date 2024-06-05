package com.fj.rems_backend.controller;

import com.fj.rems_backend.pojo.Category;
import com.fj.rems_backend.pojo.Equipment;
import com.fj.rems_backend.pojo.PageBean;
import com.fj.rems_backend.pojo.Result;
import com.fj.rems_backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //获取设备的所有分类信息
    @GetMapping("/list")
    public Result<List<Category>> list(){
        return Result.success(categoryService.list());
    }

    //添加，path传参
    @GetMapping("/add")
    public Result addOne(String name){
        if(categoryService.findByCategoryName(name)!=null){
            return Result.error("分类已存在");
        }
        categoryService.addCategory(name);
        return Result.success();
    }

    //分页查询
    //分页查询，可选的查询条件（local,id,type，name，status，department，discard，create_time,update_time）
    @PostMapping("/pagelist")
    public Result<PageBean<Category>> list(@RequestBody Map<String,Object> map){
        return Result.success(categoryService.pagelist(map));
    }

}
