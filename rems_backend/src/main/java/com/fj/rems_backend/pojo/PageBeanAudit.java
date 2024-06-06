package com.fj.rems_backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

//分页返回结果对象
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBeanAudit<T>{
    private Long total;//总条数
    private List<T> items;//当前页数据集合
    private Map<String,Long> countStatus;//不同状态的申请单数量
}
