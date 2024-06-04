package com.fj.rems_backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {
    @Select("select * from category")
    List<Map<String, Integer>> list();

    @Update("update category set num=num+#{i} where category_name=#{name}")
    void addnum(String name, int i);
}
