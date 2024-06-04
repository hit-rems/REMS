package com.fj.rems_backend.service.Impl;


import com.fj.rems_backend.mapper.CategoryMapper;
import com.fj.rems_backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Map<String, Integer>> list() {
        return categoryMapper.list();
    }
}
