package com.fj.rems_backend.mapper;

import com.fj.rems_backend.pojo.Equipment;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EquipmentMapper {

    @Select("select * from equipment where id = #{id}")
    Equipment findByEquipmentNo(Integer id);

    @Insert("insert into equipment(id,type,name,department,discard,brand,create_time,update_time,url) values(#{id},#{type},#{name},#{department},#{discard},#{brand},#{createTime},#{updateTime},#{url})")
    void add(Equipment equipment);

    //分页查询，可选的查询条件（id,type，name，status，department，discard，create_time,update_time）
    List<Equipment> pagelist(@Param("map") Map<String, Object> map,@Param("userId") Integer userId);

    @Delete("delete from equipment where id=#{id}")
    void delete(Integer id);

    void update(@Param("map") Map<String,Object> map);

    @Update("update equipment set type=#{nameNew} where type=#{nameOld}")
    void updateType(String nameOld, String nameNew);
}
