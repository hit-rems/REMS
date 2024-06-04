package com.fj.rems_backend.exception;

import com.fj.rems_backend.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e){
        e.printStackTrace();
        return Result.error((StringUtils.hasLength(e.getMessage())?e.getMessage():"操作失败"));
    }
}
