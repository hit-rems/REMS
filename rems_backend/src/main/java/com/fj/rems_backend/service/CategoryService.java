package com.fj.rems_backend.service;

import com.fj.rems_backend.pojo.Category;
import com.fj.rems_backend.pojo.PageBean;

import java.util.List;
import java.util.Map;

public interface CategoryService{
    List<Category> list();

    void addNum(String name,int i);

    Integer findByCategoryName(String s);

    void addCategory(String name);

    void subNum(String type, int i);

    PageBean<Category> pagelist(Map<String, Object> map);

    void updateCategory(String nameOld, String nameNew);

    void deleteCategory(String name);
}
