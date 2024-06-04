package com.fj.rems_backend.controller;

import com.fj.rems_backend.pojo.Equipment;
import com.fj.rems_backend.pojo.PageBean;
import com.fj.rems_backend.pojo.Result;
import com.fj.rems_backend.service.CategoryService;
import com.fj.rems_backend.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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


    @PostMapping("/uploadlist")
    public Result FileUploadList(@RequestParam("files") MultipartFile[] files) {
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String[] split = fileName.split("_");
            //判断是否存在
            Equipment equipment = equipmentService.findByEquipmentNo(Integer.parseInt(split[0]));
            if (equipment != null) {
                return Result.error(fileName+"文件的设备号已经被占用");
            }
        }
        equipmentService.uploadFileList(files);
        return Result.success();
    }

    //设备删除根据id
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        equipmentService.delete(id);
        return Result.success();
    }

    //批量删除
    @DeleteMapping("/delete")
    public Result deleteList(@RequestBody List<Integer> ids){
        for (Integer id : ids) {
            equipmentService.delete(id);
        }
        return Result.success();
    }

    //分页查询，可选的查询条件（local,id,type，name，status，department，discard，create_time,update_time）
    @PostMapping("/pagelist")
    public Result<PageBean<Equipment>> list(@RequestBody Map<String,Object> map){
        PageBean<Equipment> equipmentPageBean=equipmentService.pagelist(map);
        return Result.success(equipmentPageBean);
    }

    //修改设备信息
    @PutMapping("/update")
    public Result update(@RequestBody Map<String,Object> map){
        equipmentService.update(map);
        return Result.success();
    }
}
