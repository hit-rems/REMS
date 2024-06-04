package com.fj.rems_backend.controller;

import com.fj.rems_backend.pojo.Book;
import com.fj.rems_backend.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fj.rems_backend.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public Result add(Book book) {
        bookService.add(book);
        return Result.success();
    }

}
