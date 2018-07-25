package com.voidforce.activiti.service.userInfo;

import com.github.pagehelper.PageInfo;
import com.voidforce.activiti.bean.UserInfo;

import java.util.List;

public interface UserInfoService {

    Long insert(UserInfo userInfo);

    UserInfo getById(Long userInfoId);

    UserInfo getByUsername(String username);

    List<UserInfo> findAll(UserInfo userInfo);

    PageInfo<UserInfo> findAllForPage(Integer page, Integer limit, UserInfo userInfo);

    void delete(Long userInfoId);

    void update(UserInfo userInfo);
}
