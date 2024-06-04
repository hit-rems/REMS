package com.fj.rems_backend.mapper;

import com.fj.rems_backend.pojo.Equipment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EquipmentMapper {

    @Select("select * from equipment where id = #{id}")
    Equipment findByEquipmentNo(Integer id);

    @Insert("insert into equipment(id,type,name,status,department,discard,brand,create_time,update_time,url) values(#{id},#{type},#{name},'空闲',#{department},'正常',#{brand},now(),now(),#{url})")
    void add(Equipment equipment);

    //分页查询，可选的查询条件（id,type，name，status，department，discard，create_time,update_time）
    List<Equipment> pagelist(@Param("map") Map<String, Object> map);
}
