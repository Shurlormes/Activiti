package com.voidforce.activiti.service.departmentRole.impl;

import com.voidforce.activiti.bean.DepartmentRole;
import com.voidforce.activiti.mapper.departmentRole.DepartmentRoleMapper;
import com.voidforce.activiti.service.departmentRole.DepartmentRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
public class DepartmentRoleServiceImpl implements DepartmentRoleService {
    private Logger logger = LoggerFactory.getLogger(DepartmentRoleServiceImpl.class);

    @Autowired
    private DepartmentRoleMapper departmentRoleMapper;

    @Override
    public Long insert(DepartmentRole departmentRole) {
        List<DepartmentRole> departmentRoleList = this.findBy(departmentRole);
        if(CollectionUtils.isEmpty(departmentRoleList)) {
            departmentRoleMapper.insert(departmentRole);
        } else {
            departmentRole = departmentRoleList.get(0);
            logger.warn("{} 已有 {} 权限", departmentRole.getDepartmentName(), departmentRole.getRoleName());
        }
        return departmentRole.getDepartmentRoleId();
    }

    @Override
    public List<DepartmentRole> findBy(DepartmentRole departmentRole) {
        return departmentRoleMapper.findBy(departmentRole);
    }

}
