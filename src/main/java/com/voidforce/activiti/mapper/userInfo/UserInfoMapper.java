package com.voidforce.activiti.mapper.userInfo;

import com.voidforce.activiti.bean.UserInfo;
import com.voidforce.activiti.common.enums.DeletedEnum;
import com.voidforce.activiti.mapper.userInfo.provider.UserInfoMapperProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserInfoMapper {

    @Insert(" INERT INTO USER_INFO (USERNAME, PASSWORD, DELETED) VALUES (#{username}, #{password}, #{deleted}) ")
    @Options(keyProperty = "userInfoId", useGeneratedKeys = true)
    void insert(UserInfo userInfo);

    @Select(" SELECT * FROM USER_INFO WHERE DELETED = #{deleted} AND USER_INFO_ID = #{userInfoId} ")
    @Results(
            id = "simpleMapper",
            value = {
                    @Result(column = "USER_INFO_ID", property = "userInfoId"),
                    @Result(column = "USERNAME", property = "username"),
                    @Result(column = "PASSWORD", property = "password"),
                    @Result(column = "DELETED", property = "deleted")
            }
    )
    UserInfo getById(@Param("userInfoId") Long userInfoId, @Param("deleted") Integer deleted);

    @Select(" SELECT * FROM USER_INFO WHERE DELETED = #{deleted} AND USERNAME = #{username} ")
    @ResultMap("simpleMapper")
    UserInfo getByUsername(@Param("username") String username, @Param("deleted") Integer deleted);

    @SelectProvider(method = "findAll", type = UserInfoMapperProvider.class)
    @ResultMap("simpleMapper")
    List<UserInfo> findAll(UserInfo userInfo);
}
