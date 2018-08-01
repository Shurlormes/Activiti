package com.voidforce.activiti.mapper.userRole.provider;

import com.voidforce.activiti.bean.UserRole;
import com.voidforce.activiti.common.constant.CommonConstant;

public class UserRoleMapperProvider {

    public String deleteBy(UserRole userRole) {
        userRole.setDeleted(CommonConstant.NOT_DELETED);
        StringBuilder sql = new StringBuilder(" UPDATE USER_ROLE SET DELETED = #{deleted} " +
                " WHERE 1=1 ");

        if(userRole.getUserRoleId() != null) {
            sql.append("AND USER_ROLE_ID = #{userRoleId} ");
        }

        if(userRole.getUserInfoId() != null) {
            sql.append("AND USER_INFO_ID = #{userInfoId} ");
        }

        if(userRole.getRoleId() != null) {
            sql.append("AND ROLE_ID = #{roleId} ");
        }

        return sql.toString();
    }
}
