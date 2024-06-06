package com.fj.rems_backend.service.Impl;

import com.fj.rems_backend.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Override
    public String uploadFile(MultipartFile file) {
        //在D盘创建一个文件夹remsFile，用于存放上传的文件
        File dir = new File(uploadFolder);
        // 如果这个目录不存在，就创建它
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //获取名字
        String fileName = file.getOriginalFilename();
        //拆分
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //保存
        String newFileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        try {
            file.transferTo(new java.io.File(uploadFolder + newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回外部访问地址，http...
        String url="http://localhost:8080"+staticAccessPath.substring(0, staticAccessPath.length() - 2)+ newFileName;
        return url;
    }
}
