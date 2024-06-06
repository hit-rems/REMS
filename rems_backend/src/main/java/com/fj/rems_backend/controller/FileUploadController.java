package com.fj.rems_backend.controller;

import com.fj.rems_backend.pojo.Result;
import com.fj.rems_backend.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;
    @PostMapping("/upload")
    public Result<String> uploadFile(MultipartFile file) throws Exception {
        String filePath = fileUploadService.uploadFile(file);
        return Result.success(filePath);
    }
}
