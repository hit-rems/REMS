package com.fj.rems_backend.service;

import com.fj.rems_backend.pojo.Admin;
import com.fj.rems_backend.pojo.PageBean;
import com.fj.rems_backend.pojo.PageBeanAudit;

import java.util.Map;

public interface SuperService {
    PageBeanAudit<Admin> pagelist(Map<String, Object> map);

    void update(Map<String, Object> map);
}
