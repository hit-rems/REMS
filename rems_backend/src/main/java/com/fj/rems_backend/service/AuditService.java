package com.fj.rems_backend.service;

import com.fj.rems_backend.pojo.Audit;
import com.fj.rems_backend.pojo.PageBean;
import com.fj.rems_backend.pojo.PageBeanAudit;

import java.util.Map;

public interface AuditService {
    PageBeanAudit<Audit> pagelist(Map<String, Object> map);

    void update(Map<String, Object> map);
}
