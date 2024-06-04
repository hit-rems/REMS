package com.fj.rems_backend.service;

import com.fj.rems_backend.pojo.Equipment;
import com.fj.rems_backend.pojo.PageBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface EquipmentService {

    Equipment findByEquipmentNo(Integer id);

    void add(Equipment equipment);

    PageBean<Equipment> pagelist(Map<String, Object> map);

    String uploadFile(MultipartFile file);

    Map<String, Integer> category();
}
