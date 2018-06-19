package com.voidforce.activiti.bean;

public class DepartmentRole {

    private Long departmentRoleId;

    private Long departmentId;

    private Long roleId;

    private String departmentName;

    private String roleName;

    public Long getDepartmentRoleId() {
        return departmentRoleId;
    }

    public void setDepartmentRoleId(Long departmentRoleId) {
        this.departmentRoleId = departmentRoleId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
