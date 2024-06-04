package com.fj.rems_backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {
    @Select("select * from category")
    List<Map<String, Integer>> list();

    @Update("update category set num=num+#{i} where name=#{name}")
    void addnum(String name, int i);

    @Select("select num from category where name=#{s}")
    Integer findByCategoryName(String s);

    @Insert("insert into category(name,num) values(#{name},0)")
    void addCategory(String name);
}
