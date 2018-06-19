package com.voidforce.activiti.mapper.departmentRole;

import com.voidforce.activiti.bean.DepartmentRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DepartmentRoleMapper {

    @Insert("INSERT INTO DEPARTMENT_ROLE (DEPARTMENT_ID, ROLE_ID) VALUES (#{departmentId}, #{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "departmentRoleId")
    void insert(DepartmentRole departmentRole);

    @SelectProvider(type = DepartmentRoleProvider.class, method = "findBy")
    @Results(
            id = "simpleMapper",
            value = {
                    @Result(property = "departmentRoleId", column = "DEPARTMENT_ROLE_ID"),
                    @Result(property = "departmentId", column = "DEPARTMENT_ID"),
                    @Result(property = "roleId", column = "ROLE_ID"),
                    @Result(property = "departmentName", column = "DEPARTMENT_NAME"),
                    @Result(property = "roleName", column = "ROLE_NAME")
            }
    )
    List<DepartmentRole> findBy(DepartmentRole departmentRole);
}
