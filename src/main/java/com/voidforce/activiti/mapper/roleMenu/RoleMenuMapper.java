package com.voidforce.activiti.mapper.roleMenu;

import com.voidforce.activiti.bean.RoleMenu;
import com.voidforce.activiti.mapper.roleMenu.provider.RoleMenuMapperProvider;
import org.apache.ibatis.annotations.*;

public interface RoleMenuMapper {

    @Insert(" INSERT INTO ROLE_MENU (ROLE_ID, MENU_ID, DELETED) " +
            " VALUES (#{roleId}, #{menuId}, #{deleted}) ")
    @Options(keyProperty = "roleMenuId", useGeneratedKeys = true)
    void insert(RoleMenu roleMenu);


    @Select(" SELECT * FROM ROLE_MENU WHERE DELETED = #{deleted} AND ROLE_ID = #{roleId} AND MENU_ID = #{menuId} ")
    @Results(
            id = "simpleMapper",
            value = {
                    @Result(column = "ROLE_MENU_ID", property = "roleMenuId"),
                    @Result(column = "ROLE_ID", property = "roleId"),
                    @Result(column = "MENU_ID", property = "menuId"),
                    @Result(column = "DELETED", property = "deleted")
            }
    )
    RoleMenu getByRoleIdAndMenuId(@Param("roleId") Long roleId, @Param("menuId") Long menuId, @Param("deleted") Integer deleted);


    @UpdateProvider(method = "deleteBy", type = RoleMenuMapperProvider.class)
    void deleteBy(RoleMenu roleMenu);

}
