package com.voidforce.activiti.mapper.userInfo;

import com.voidforce.activiti.bean.UserInfo;
import org.apache.ibatis.annotations.*;

public interface UserInfoMapper {

    @Insert("INERT INTO USER_INFO (NAME, PASSWORD, DEPARTMENT_ID) VALUES (#{name}, #{password}, #{departmentId})")
    @Options(useGeneratedKeys = true, keyProperty = "userInfoId")
    void insert(UserInfo userInfo);

    @Select("SELECT * FROM USER_INFO WHERE USER_INFO_ID = #{userInfoId}")
    @Results(
            id = "simpleMapper",
            value = {
                    @Result(property = "userInfoId", column = "USER_INFO_ID"),
                    @Result(property = "name", column = "NAME"),
                    @Result(property = "password", column = "PASSWORD"),
                    @Result(property = "departmentId", column = "DEPARTMENT_ID"),
            }
    )
    UserInfo getById(Long userInfoId);

    @Select("SELECT * FROM USER_INFO WHERE NAME = #{name}")
    @ResultMap("simpleMapper")
    UserInfo getByName(String name);
}
