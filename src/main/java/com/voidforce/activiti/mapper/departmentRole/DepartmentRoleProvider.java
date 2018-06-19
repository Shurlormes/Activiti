package com.voidforce.activiti.mapper.departmentRole;

import com.voidforce.activiti.bean.DepartmentRole;

public class DepartmentRoleProvider {

    public String findBy(DepartmentRole departmentRole) {
        StringBuilder sql = new StringBuilder("SELECT DR.*, D.NAME AS DEPARTMENT_NAME, R.NAME AS ROLE_NAME FROM DEPARTMENT_ROLE DR " +
                "INNER JOIN DEPARTMENT D ON D.DEPARTMENT_ID = DR.DEPARTMENT_ID " +
                "INNER JOIN ROLE R ON R.ROLE_ID = DR.ROLE_ID " +
                "WHERE 1=1 ");

        if(departmentRole.getDepartmentRoleId() != null) {
            sql.append("AND DR.DEPARTMENT_ROLE_ID = #{departmentRoleId} ");
        }
        if(departmentRole.getDepartmentId() != null) {
            sql.append("AND DR.DEPARTMENT_ID = #{departmentId} ");
        }
        if(departmentRole.getRoleId() != null) {
            sql.append("AND DR.ROLE_ID = #{roleId} ");
        }
        return sql.toString();
    }
}
