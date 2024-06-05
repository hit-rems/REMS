package com.fj.rems_backend.service.Impl;


import com.fj.rems_backend.mapper.CategoryMapper;
import com.fj.rems_backend.mapper.EquipmentMapper;
import com.fj.rems_backend.pojo.Category;
import com.fj.rems_backend.pojo.PageBean;
import com.fj.rems_backend.service.CategoryService;
import com.fj.rems_backend.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    @Override
    public void addNum(String name,int i) {
        categoryMapper.addnum(name,i);
    }

    @Override
    public Integer findByCategoryName(String s) {
        return categoryMapper.findByCategoryName(s);
    }

    @Override
    public void addCategory(String name) {
        categoryMapper.addCategory(name);
    }

    @Override
    public void subNum(String type, int i) {
        categoryMapper.addnum(type,-i);
    }

    @Override
    public PageBean<Category> pagelist(Map<String, Object> map) {
        //1.创建PageBean对象
        PageBean<Category> pb = new PageBean<>();
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);

        //3.调用mapper
        List<Category> as = categoryMapper.list();
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<Category> p = (Page<Category>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public void updateCategory(String nameOld, String nameNew) {
        //修改设备表中的分类
        equipmentMapper.updateType(nameOld,nameNew);
        //修改分类表中的分类
        categoryMapper.updateCategory(nameOld,nameNew);
    }
}
