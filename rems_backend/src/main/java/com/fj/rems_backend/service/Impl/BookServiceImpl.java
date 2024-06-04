package com.fj.rems_backend.service.Impl;

import com.fj.rems_backend.mapper.BookMapper;
import com.fj.rems_backend.pojo.Book;
import com.fj.rems_backend.service.BookService;
import com.fj.rems_backend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public void add(Book book) {
        Map<String, Object> map = ThreadLocalUtil.get();
        book.setUserId((Integer) map.get("id"));
        book.setStatus("待审核");
        book.setCreateTime(LocalDateTime.now());
        book.setUpdateTime(LocalDateTime.now());
        bookMapper.add(book);
    }
}
