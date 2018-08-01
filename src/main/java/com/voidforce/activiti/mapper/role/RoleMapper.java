package com.voidforce.activiti.mapper.role;

import com.voidforce.activiti.bean.Role;
import com.voidforce.activiti.mapper.role.provider.RoleMapperProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleMapper {

    @Insert(" INSERT INTO ROLE (ROLE, ROLE_NAME, DELETED) " +
            " VALUES (#{role}, #{roleName}, #{deleted}) ")
    @Options(keyProperty = "roleId", useGeneratedKeys = true)
    void insert(Role role);


    @Select(" SELECT * FROM ROLE WHERE DELETED = #{deleted} AND ROLE_ID = #{roleId} ")
    @Results(
        id = "simpleMapper",
        value = {
            @Result(column = "ROLE_ID", property = "roleId"),
            @Result(column = "ROLE", property = "role"),
            @Result(column = "ROLE_NAME", property = "roleName"),
            @Result(column = "DELETED", property = "deleted")
        }
    )
    Role getById(@Param("roleId") Long roleId, @Param("deleted") Integer deleted);

    @Select(" SELECT * FROM ROLE WHERE DELETED = #{deleted} AND ROLE = #{role} ")
    @ResultMap("simpleMapper")
    Role getByRole(@Param("role") String role, @Param("deleted") Integer deleted);

    @Select(" SELECT T1.* FROM ROLE T1 " +
            " INNER JOIN USER_ROLE T2 " +
            " ON T2.DELETED = #{deleted} AND USER_INFO_ID = #{userInfoId} AND T2.ROLE_ID = T1.ROLE_ID " +
            " WHERE T1.DELETED = #{deleted} ")
    @ResultMap("simpleMapper")
    List<Role> findByUserInfoId(@Param("userInfoId") Long userInfoId, @Param("deleted") Integer deleted);


    @SelectProvider(method = "findAll", type = RoleMapperProvider.class)
    @ResultMap("simpleMapper")
    List<Role> findAll(Role role);

    @UpdateProvider(method = "update", type = RoleMapperProvider.class)
    void update(Role role);

    @Update(" UPDATE ROLE SET DELETED = 1 WHERE ROLE_ID = #{roleId} ")
    void delete(Long roleId);
}
