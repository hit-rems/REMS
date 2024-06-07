package com.fj.rems_backend.service;

import com.fj.rems_backend.pojo.Book;

import java.util.List;
import java.util.Map;

public interface BookService {
    void add(Book book);

    List<Boolean> query(Map<String, Object> map);
}
