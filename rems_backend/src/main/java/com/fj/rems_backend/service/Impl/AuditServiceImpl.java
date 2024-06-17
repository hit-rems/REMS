package com.fj.rems_backend.service.Impl;

import com.fj.rems_backend.mapper.AuditMapper;
import com.fj.rems_backend.mapper.UserMapper;
import com.fj.rems_backend.pojo.*;
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
    @Autowired
    private UserMapper userMapper;
    @Override
    public PageBeanAudit<Audit> pagelist(Map<String, Object> map, int id) {
        //获取用户角色
        User user = userMapper.findByUserId(id);
        String role = user.getType();
        String department = user.getDepartment();
        //若map里有role则使用map里的role
        if (map.containsKey("role")) {
            role = (String) map.get("role");
            user.setType(role);
        }
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
        if (role.equals("超级管理员")) {
            department = null;
        }
        List<Audit> as = auditMapper.list(type,id,role,department);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<Audit> p = (Page<Audit>) as;
        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        //查询各状态的数量
        Map<String, Long> mapNum = new HashMap<>();
        List<Map<String, Object>> countStatus = auditMapper.countStatus(id,role);
        //统计总数量
        Long total = 0L;
        for (Map<String, Object> map1 : countStatus) {
            Long num = (Long) map1.get("num");
            total += num;
            mapNum.put((String)map1.get("status"),num);
        }
        mapNum.put("待审核",mapNum.getOrDefault("待审核",0L));
        mapNum.put("已通过",mapNum.getOrDefault("已通过",0L));
        mapNum.put("未通过",mapNum.getOrDefault("未通过",0L));
        //统计待使用的数量
        int num = auditMapper.countWaitToUse(id,role);
        mapNum.put("待使用",(long)num);
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

    @Override
    public void updatelist(Map<String, Object> map) {
        List<Integer> idList= (List<Integer>) map.get("idList");
        String status = (String) map.get("status");
        auditMapper.updatelist(idList,status);
    }
}
