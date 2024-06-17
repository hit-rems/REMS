package com.fj.rems_backend.mapper;

import com.fj.rems_backend.pojo.Audit;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface AuditMapper {
    List<Audit> list(String type, int id,String role,String department);

    //查询不同状态的申请单数量
    @MapKey("status")
    List<Map<String,Object>> countStatus(int id,String role,String department);

    @Update("update book set status=#{status} where id=#{id}")
    void update(int id, String status);

    @Update("<script>update book set status=#{status} where id in <foreach collection='idList' item='id' open='(' separator=',' close=')'>#{id}</foreach></script>")
    void updatelist(List<Integer> idList, String status);

    @Select("<script>"
            + "select count(*) from book"
            + " where status='已通过' AND end_time > now() AND (user_id=#{id} OR #{role} != '学生')"
            + "<if test='department != null'>"
            + " AND department=#{department}"
            + "</if>"
            + "</script>")
    int countWaitToUse(int id, String role, String department);


}
