package com.fj.rems_backend.mapper;

import com.fj.rems_backend.pojo.Audit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AuditMapper {
    List<Audit> list(String type);

    //查询不同状态的申请单数量
    @Select("select status,count(*) as num from book group by status")
    List<Map<String,Object>> countStatus();
}
