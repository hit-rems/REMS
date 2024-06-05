package com.fj.rems_backend.mapper;

import com.fj.rems_backend.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {
    @Select("select * from category")
    List<Category> list();

    @Update("update category set num=num+#{i} where name=#{name}")
    void addnum(String name, int i);

    @Select("select num from category where name=#{s}")
    Integer findByCategoryName(String s);

    @Insert("insert into category(name,num) values(#{name},0)")
    void addCategory(String name);

    @Update("update category set name=#{nameNew} where name=#{nameOld}")
    void updateCategory(String nameOld, String nameNew);
}
