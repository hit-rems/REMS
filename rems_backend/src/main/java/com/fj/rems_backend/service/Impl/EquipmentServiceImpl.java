package com.fj.rems_backend.service.Impl;

import com.fj.rems_backend.mapper.EquipmentMapper;
import com.fj.rems_backend.pojo.Equipment;
import com.fj.rems_backend.pojo.PageBean;
import com.fj.rems_backend.service.CategoryService;
import com.fj.rems_backend.service.EquipmentService;
import com.fj.rems_backend.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private CategoryService categoryService;
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Override
    public Equipment findByEquipmentNo(Integer id) {
        return equipmentMapper.findByEquipmentNo(id);
    }

    @Override
    public void add(Equipment equipment) {
        equipment.setDiscard("正常");
        equipment.setCreateTime(LocalDateTime.now());
        equipment.setUpdateTime(LocalDateTime.now());
        categoryService.addNum(equipment.getType(),1);
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
        //获取当前用户的id
        Map<String,Object> user = ThreadLocalUtil.get();
        Integer userId = (Integer) user.get("id");

        //3.调用mapper
        List<Equipment> as = equipmentMapper.pagelist(map,userId);
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

    @Override
    public void uploadFileList(MultipartFile[] files) {
        for (MultipartFile file : files) {
            //获取名字
            String fileName = file.getOriginalFilename();
            //拆分
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            //将前缀按_拆分
            String[] split = fileName.substring(0, fileName.lastIndexOf(".")).split("_");
            Equipment equipment = new Equipment();
            equipment.setId(Integer.parseInt(split[0]));
            equipment.setType(split[1]);
            equipment.setName(split[2]);
            equipment.setDepartment(split[3]);
            equipment.setDiscard("正常");
            equipment.setBrand(split[4]);
            equipment.setCreateTime(LocalDateTime.now());
            equipment.setUpdateTime(LocalDateTime.now());
            String newFileName = UUID.randomUUID().toString().replace("-", "") + suffix;
            try {
                file.transferTo(new java.io.File(uploadFolder + newFileName));
                equipment.setUrl(staticAccessPath.substring(0, staticAccessPath.length() - 2)+ newFileName);
                categoryService.addNum(equipment.getType(),1);
                equipmentMapper.add(equipment);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Integer id) {
        categoryService.subNum(equipmentMapper.findByEquipmentNo(id).getType(),1);
        equipmentMapper.delete(id);
    }

    @Override
    public void update(Map<String,Object> map) {
        equipmentMapper.update(map);
    }

    @Override
    public void updateType(String nameOld, String nameNew) {
        equipmentMapper.updateType(nameOld,nameNew);
    }

}
