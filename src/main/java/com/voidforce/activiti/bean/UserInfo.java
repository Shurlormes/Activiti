package com.voidforce.activiti.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserInfo extends BaseUser {

    private Long UserInfoId;

    private String name;

    @JsonIgnore
    private String password;

    private Long departmentId;

    public Long getUserInfoId() {
        return UserInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        UserInfoId = userInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String getUsername() {
        return this.name;
    }
}
