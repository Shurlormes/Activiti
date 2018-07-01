package com.voidforce.activiti.mapper.role;

import com.voidforce.activiti.bean.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleMapper {

    @Insert(" INSERT INTO ROLE (ROLE, ROLE_NAME, DELETED) " +
            " VALUES (#{role}, #{roleName}, #{deleted}) ")
    @Options(keyProperty = "roleId", useGeneratedKeys = true)
    void insert(Role role);

    @Select(" SELECT * FROM ROLE WHERE DELETED = #{deleted} AND ROLE = #{role} ")
    @Results(
            id = "simpleMapper",
            value = {
                    @Result(column = "ROLE_ID", property = "roleId"),
                    @Result(column = "ROLE", property = "role"),
                    @Result(column = "ROLE_NAME", property = "roleName"),
                    @Result(column = "DELETED", property = "deleted")
            }
    )
    Role getByRole(@Param("role") String role, @Param("deleted") Integer deleted);

    @Select(" SELECT T1.* FROM ROLE T1 " +
            " INNER JOIN USER_ROLE T2 " +
            " ON T2.DELETED = #{deleted} AND USER_INFO_ID = #{userInfoId} AND T2.ROLE_ID = T1.ROLE_ID " +
            " WHERE T1.DELETED = #{deleted} ")
    @ResultMap("simpleMapper")
    List<Role> findByUserInfoId(@Param("userInfoId") Long userInfoId, @Param("deleted") Integer deleted);
}
