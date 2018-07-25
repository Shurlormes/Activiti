package com.voidforce.activiti.service.userInfo.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.voidforce.activiti.bean.UserInfo;
import com.voidforce.activiti.common.enums.DeletedEnum;
import com.voidforce.activiti.mapper.userInfo.UserInfoMapper;
import com.voidforce.activiti.service.activiti.SimpleIdentityService;
import com.voidforce.activiti.service.userInfo.UserInfoService;
import com.voidforce.activiti.util.EncodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    private Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private SimpleIdentityService simpleIdentityService;

    @Override
    public Long insert(UserInfo userInfo) {
        UserInfo dbUserInfo = this.getByUsername(userInfo.getUsername());
        if(dbUserInfo == null) {
            userInfo.setPassword(EncodeUtil.encodeByBCrypt(userInfo.getPassword()));
            userInfo.setDeleted(DeletedEnum.NOT_DELETED.getCode());
            userInfoMapper.insert(userInfo);

            simpleIdentityService.updateUser(userInfo.getUserInfoId().toString(), userInfo.getUsername());

            return userInfo.getUserInfoId();
        }
        logger.warn("{} 已存在", userInfo.getUsername());
        return dbUserInfo.getUserInfoId();
    }

    @Override
    public UserInfo getById(Long userInfoId) {
        return userInfoMapper.getById(userInfoId, DeletedEnum.NOT_DELETED.getCode());
    }

    @Override
    public UserInfo getByUsername(String username) {
        return userInfoMapper.getByUsername(username, DeletedEnum.NOT_DELETED.getCode());
    }

    @Override
    public List<UserInfo> findAll(UserInfo userInfo) {
        return userInfoMapper.findAll(userInfo);
    }

    @Override
    public PageInfo<UserInfo> findAllForPage(Integer page, Integer limit, UserInfo userInfo) {
        PageHelper.startPage(page, limit);
        List<UserInfo> userInfoList = this.findAll(userInfo);
        return new PageInfo<>(userInfoList);
    }

    @Override
    public void delete(Long userInfoId) {
        userInfoMapper.delete(userInfoId);
    }

    @Override
    public void update(UserInfo userInfo) {
        if(StringUtils.isNotEmpty(userInfo.getPassword())) {
            userInfo.setPassword(EncodeUtil.encodeByBCrypt(userInfo.getPassword()));
        }

        userInfoMapper.update(userInfo);

        simpleIdentityService.updateUser(userInfo.getUserInfoId().toString(), userInfo.getUsername());
    }
}
