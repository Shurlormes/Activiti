package com.voidforce.activiti.service.department.impl;

import com.voidforce.activiti.bean.Department;
import com.voidforce.activiti.mapper.department.DepartmentMapper;
import com.voidforce.activiti.service.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Long insert(Department department) {
        departmentMapper.insert(department);
        return department.getDepartmentId();
    }

    @Override
    public Department getById(Long departmentId) {
        return departmentMapper.getById(departmentId);
    }

    @Override
    public Department getParentByChildId(Long departmentId) {
        return departmentMapper.getParentByChildId(departmentId);
    }
}
