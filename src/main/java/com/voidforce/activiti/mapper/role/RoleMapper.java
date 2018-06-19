package com.voidforce.activiti.mapper.role;

import com.voidforce.activiti.bean.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleMapper {

    @Insert("INSERT INTO ROLE (ROLE_NAME) VALUES (#{roleName})")
    @Options(useGeneratedKeys = true, keyProperty = "roleId")
    void insert(Role role);


    @Select("SELECT * FROM ROLE WHERE ROLE_ID = #{roleId}")
    @Results(
            id = "simpleMapper",
            value = {
                    @Result(property = "roleId", column = "ROLE_ID"),
                    @Result(property = "name", column = "NAME")
            }
    )
    Role getById(Long roleId);

    @Select("SELECT * FROM ROLE WHERE NAME = #{name}")
    @ResultMap("simpleMapper")
    Role getByName(String name);
}
