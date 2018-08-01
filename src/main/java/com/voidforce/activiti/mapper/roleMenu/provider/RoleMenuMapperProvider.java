package com.voidforce.activiti.mapper.roleMenu.provider;

import com.voidforce.activiti.bean.RoleMenu;
import com.voidforce.activiti.common.constant.CommonConstant;

public class RoleMenuMapperProvider {

    public String deleteBy(RoleMenu roleMenu) {
        roleMenu.setDeleted(CommonConstant.NOT_DELETED);
        StringBuilder sql = new StringBuilder(" UPDATE ROLE_MENU SET DELETED = #{deleted} " +
                " WHERE 1=1 ");

        if(roleMenu.getRoleMenuId() != null) {
            sql.append("AND ROLE_MENU_ID = #{roleMenuId} ");
        }

        if(roleMenu.getRoleId() != null) {
            sql.append("AND ROLE_ID = #{roleId} ");
        }

        if(roleMenu.getMenuId() != null) {
            sql.append("AND MENU_ID = #{menuId} ");
        }

        return sql.toString();
    }

}
