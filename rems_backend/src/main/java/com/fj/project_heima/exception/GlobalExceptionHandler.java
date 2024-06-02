package com.fj.project_heima.exception;

import com.fj.project_heima.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e){
        return Result.error((StringUtils.hasLength(e.getMessage())?e.getMessage():"操作失败"));
    }
}
