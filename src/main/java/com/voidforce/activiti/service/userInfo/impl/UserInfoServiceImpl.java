package com.voidforce.activiti.service.userInfo.impl;

import com.voidforce.activiti.bean.UserInfo;
import com.voidforce.activiti.mapper.userInfo.UserInfoMapper;
import com.voidforce.activiti.service.userInfo.UserInfoService;
import com.voidforce.activiti.util.EncodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    private Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Long insert(UserInfo userInfo) {
        UserInfo dbUserInfo = this.getByName(userInfo.getName());
        if(dbUserInfo == null) {
            userInfo.setPassword(EncodeUtil.encodeByBCrypt(userInfo.getPassword()));
            userInfoMapper.insert(userInfo);
            return userInfo.getUserInfoId();
        }
        logger.warn("{} 已存在", userInfo.getName());
        return dbUserInfo.getUserInfoId();
    }

    @Override
    public UserInfo getById(Long userInfoId) {
        return userInfoMapper.getById(userInfoId);
    }

    @Override
    public UserInfo getByName(String name) {
        return userInfoMapper.getByName(name);
    }
}
