package com.fj.rems_backend.service.Impl;

import com.fj.rems_backend.mapper.EquipmentMapper;
import com.fj.rems_backend.pojo.Equipment;
import com.fj.rems_backend.pojo.PageBean;
import com.fj.rems_backend.service.EquipmentService;
import com.fj.rems_backend.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentMapper equipmentMapper;

    @Override
    public Equipment findByEquipmentNo(Integer id) {
        return equipmentMapper.findByEquipmentNo(id);
    }

    @Override
    public void add(Equipment equipment) {
        equipmentMapper.add(equipment);
    }

    @Override
    public PageBean<Equipment> pagelist(Map<String, Object> map) {

        //1.创建PageBean对象
        PageBean<Equipment> pb = new PageBean<>();
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);

        //3.调用mapper
        List<Equipment> as = equipmentMapper.pagelist(map);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<Equipment> p = (Page<Equipment>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        //在D盘创建一个文件夹remsFile，用于存放上传的文件
        File dir = new File("D:/remsFile");
        // 如果这个目录不存在，就创建它
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = "D:/remsFile/";
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        try {
            file.transferTo(new java.io.File(filePath + newFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath + newFileName;
    }
}
