package com.fj.rems_backend.service.Impl;

import com.fj.rems_backend.mapper.AuditMapper;
import com.fj.rems_backend.pojo.Audit;
import com.fj.rems_backend.pojo.Category;
import com.fj.rems_backend.pojo.PageBean;
import com.fj.rems_backend.pojo.PageBeanAudit;
import com.fj.rems_backend.service.AuditService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuditServiceImpl implements AuditService {
    @Autowired
    private AuditMapper auditMapper;
    @Override
    public PageBeanAudit<Audit> pagelist(Map<String, Object> map) {
        //1.创建PageBean对象
        PageBeanAudit<Audit> pb = new PageBeanAudit<>();
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        String type = (String) map.get("queryType");
        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);
        //3.调用mapper
        if (type.equals("全部")) {
            type = null;
        }
        List<Audit> as = auditMapper.list(type);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<Audit> p = (Page<Audit>) as;
        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        //查询各状态的数量
        Map<String, Long> mapNum = new HashMap<>();
        List<Map<String, Object>> countStatus = auditMapper.countStatus();
        //统计总数量
        Long total = 0L;
        for (Map<String, Object> map1 : countStatus) {
            Long num = (Long) map1.get("num");
            total += num;
            mapNum.put((String)map1.get("status"),num);
        }
        mapNum.put("全部",total);
        pb.setCountStatus(mapNum);
        return pb;
    }

    @Override
    public void update(Map<String, Object> map) {
        int id = (int) map.get("id");
        String status = (String) map.get("status");
        auditMapper.update(id,status);
    }
}
