package com.voidforce.activiti.mapper.userRole;

import com.voidforce.activiti.bean.UserRole;
import com.voidforce.activiti.mapper.userRole.provider.UserRoleMapperProvider;
import org.apache.ibatis.annotations.*;

public interface UserRoleMapper {


    @Insert(" INSERT INTO USER_ROLE (USER_INFO_ID, ROLE_ID, DELETED) " +
            " VALUES (#{userInfoId}, #{roleId}, #{deleted}) ")
    @Options(useGeneratedKeys = true, keyProperty = "userRoleId")
    void insert(UserRole userRole);

    @Select(" SELECT * FROM USER_ROLE WHERE DELETED = #{deleted} AND USER_INFO_ID = #{userInfId} AND ROLE_ID = #{roleId} ")
    @Results(
            id = "simpleMapper",
            value = {
                    @Result(column = "USER_ROLE_ID", property = "userRoleId"),
                    @Result(column = "USER_INFO_ID", property = "userInfoId"),
                    @Result(column = "ROLE_ID", property = "roleId"),
                    @Result(column = "DELETED", property = "deleted")
            }
    )
    UserRole getByUserInfoIdAndRoleId(@Param("userInfoId") Long userInfoId, @Param("roleId") Long roleId, @Param("deleted") Integer deleted);

    @UpdateProvider(method = "deleteBy", type = UserRoleMapperProvider.class)
    void deleteBy(UserRole userRole);
}
