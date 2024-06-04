package com.fj.rems_backend.service;

import java.util.List;
import java.util.Map;

public interface CategoryService{
    List<Map<String, Integer>> list();

    void addNum(String name,int i);

    Integer findByCategoryName(String s);

    void addCategory(String name);

    void subNum(String type, int i);
}
