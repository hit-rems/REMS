package com.fj.rems_backend.service;

import java.util.List;
import java.util.Map;

public interface CategoryService{
    List<Map<String, Integer>> list();

    void addOne(String name);

}
