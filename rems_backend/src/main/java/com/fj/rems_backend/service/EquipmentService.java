package com.fj.rems_backend.service;

import com.fj.rems_backend.pojo.Equipment;
import com.fj.rems_backend.pojo.PageBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface EquipmentService {

    Equipment findByEquipmentNo(Integer id);

    void add(Equipment equipment, MultipartFile file);

    PageBean<Equipment> pagelist(Map<String, Object> map);

    void uploadFileList(MultipartFile[] files);

    void delete(Integer id);

    void update(Map<String,Object> map);

    int findBookEquipmentNo(Integer id);

}
