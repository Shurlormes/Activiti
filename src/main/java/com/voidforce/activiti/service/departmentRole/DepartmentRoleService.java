package com.voidforce.activiti.service.departmentRole;

import com.voidforce.activiti.bean.DepartmentRole;

import java.util.List;

public interface DepartmentRoleService {

    Long insert(DepartmentRole departmentRole);

    List<DepartmentRole> findBy(DepartmentRole departmentRole);
}
