package com.voidforce.activiti.service.userRole.impl;

import com.voidforce.activiti.bean.UserRole;
import com.voidforce.activiti.common.constant.CommonConstant;
import com.voidforce.activiti.mapper.userRole.UserRoleMapper;
import com.voidforce.activiti.service.userRole.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Long insert(UserRole userRole) {
        UserRole dbUserRole = this.getByUserInfoIdAndRoleId(userRole.getUserInfoId(), userRole.getRoleId());
        if(dbUserRole == null) {
            userRoleMapper.insert(userRole);
            return userRole.getUserRoleId();
        }
        logger.warn("用户 {} 已有 {} 权限", userRole.getUserInfoId(), userRole.getRoleId());
        return dbUserRole.getUserRoleId();
    }

    @Override
    public UserRole getByUserInfoIdAndRoleId(Long userInfoId, Long roleId) {
        return userRoleMapper.getByUserInfoIdAndRoleId(userInfoId, roleId, CommonConstant.NOT_DELETED);
    }

    @Override
    public void deleteBy(UserRole userRole) {
        userRoleMapper.deleteBy(userRole);
    }
}
