package com.voidforce.activiti.service.userInfo;

import com.voidforce.activiti.bean.UserInfo;

public interface UserInfoService {

    Long insert(UserInfo userInfo);

    UserInfo getById(Long userInfoId);

    UserInfo getByName(String name);
}
