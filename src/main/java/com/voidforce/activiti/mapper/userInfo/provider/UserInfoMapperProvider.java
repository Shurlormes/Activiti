package com.voidforce.activiti.mapper.userInfo.provider;

import com.voidforce.activiti.bean.UserInfo;
import com.voidforce.activiti.common.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;

public class UserInfoMapperProvider {

    public String findAll(UserInfo userInfo) {
        userInfo.setDeleted(CommonConstant.NOT_DELETED);
        StringBuilder sql = new StringBuilder(" SELECT * FROM USER_INFO " +
                " WHERE DELETED = #{deleted} ");

        if(userInfo.getUserInfoId() != null) {
            sql.append(" AND USER_INFO_ID = #{userInfoId} ");
        }

        if(StringUtils.isNotEmpty(userInfo.getUsername())) {
            userInfo.setUsername("%" + userInfo.getUsername() + "%");
            sql.append(" AND USERNAME LIKE #{username} ");
        }

        return sql.toString();
    }

    public String update(UserInfo userInfo) {
        StringBuilder sql = new StringBuilder(" UPDATE USER_INFO SET ");

        if(StringUtils.isNotEmpty(userInfo.getUsername())) {
            sql.append(" USERNAME = #{username}, ");
        }

        if(StringUtils.isNotEmpty(userInfo.getPassword())) {
            sql.append(" PASSWORD = #{password}, ");
        }

        sql.deleteCharAt(sql.lastIndexOf(","));

        sql.append(" WHERE USER_INFO_ID = #{userInfoId} ");

        return sql.toString();
    }
}
