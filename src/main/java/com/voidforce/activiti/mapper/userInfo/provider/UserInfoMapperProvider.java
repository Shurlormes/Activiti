package com.voidforce.activiti.mapper.userInfo.provider;

import com.voidforce.activiti.bean.UserInfo;
import com.voidforce.activiti.common.enums.DeletedEnum;
import org.apache.ibatis.annotations.*;

public class UserInfoMapperProvider {

    public String findAll(UserInfo userInfo) {
        userInfo.setDeleted(DeletedEnum.NOT_DELETED.getCode());
        StringBuilder sql = new StringBuilder(" SELECT * FROM USER_INFO " +
                " WHERE DELETED = #{deleted} ");

        if(userInfo.getUserInfoId() != null) {
            sql.append(" AND USER_INFO_ID = #{userInfoId} ");
        }

        if(userInfo.getUsername() != null) {
            sql.append(" AND USERNAME = #{username} ");
        }

        return sql.toString();
    }

}
