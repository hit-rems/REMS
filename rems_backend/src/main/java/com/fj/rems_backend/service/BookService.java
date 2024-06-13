package com.fj.rems_backend.service;

import com.fj.rems_backend.pojo.Audit;
import com.fj.rems_backend.pojo.Book;

import java.util.List;
import java.util.Map;

public interface BookService {
    void add(Map<String, Object> map);

    List<Boolean> query(Map<String, Object> map);

    List<Audit> userInfo();
}
