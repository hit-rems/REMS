package com.fj.rems_backend.mapper;

import com.fj.rems_backend.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface SuperMapper {
    @Select("<script>"
            + "select * from authorization"
            + "<if test='type != null'>"
            + " WHERE status = #{type}"
            + "</if>"
            + "</script>")
    List<Admin> pagelist(String type);

    @Select("select status,count(*) as num from authorization group by status")
    List<Map<String, Object>> countStatus();

    @Update("update authorization set status=#{status} where id=#{id}")
    void update(int id, String status);
}
