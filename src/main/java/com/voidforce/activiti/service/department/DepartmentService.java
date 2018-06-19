package com.voidforce.activiti.service.department;

import com.voidforce.activiti.bean.Department;

public interface DepartmentService {

    Long insert(Department department);

    Department getById(Long departmentId);

    Department getParentByChildId(Long departmentId);
}
