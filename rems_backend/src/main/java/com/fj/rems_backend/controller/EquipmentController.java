package com.fj.rems_backend.controller;

import com.fj.rems_backend.pojo.Equipment;
import com.fj.rems_backend.pojo.PageBean;
import com.fj.rems_backend.pojo.Result;
import com.fj.rems_backend.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    //添加设备
    @PostMapping("/add")
    public Result add(Equipment equipment){
        //查询设备号是否存在
        Equipment e = equipmentService.findByEquipmentNo(equipment.getId());
        if (e!=null){
            return Result.error("设备号已经被占用");
        }else {
            equipmentService.add(equipment);
            return Result.success();
        }
    }

    //设备照片文件上传
    @PostMapping("/upload")
    public Result<String> uploadFile(MultipartFile file) throws Exception {
        String filePath = equipmentService.uploadFile(file);
        return Result.success(filePath);
    }

    //分页查询，可选的查询条件（id,type，name，status，department，discard，create_time,update_time）
    @PostMapping("/pagelist")
    public Result<PageBean<Equipment>> list(@RequestBody Map<String,Object> map){
        PageBean<Equipment> equipmentPageBean=equipmentService.pagelist(map);
        return Result.success(equipmentPageBean);
    }

}
