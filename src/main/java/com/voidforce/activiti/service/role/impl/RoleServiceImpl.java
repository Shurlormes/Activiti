package com.voidforce.activiti.service.role.impl;

import com.voidforce.activiti.bean.Role;
import com.voidforce.activiti.common.enums.DeletedEnum;
import com.voidforce.activiti.mapper.role.RoleMapper;
import com.voidforce.activiti.service.role.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Long insert(Role role) {
        Role dbRole = this.getByRole(role.getRole());
        if(dbRole == null) {
            roleMapper.insert(role);
            return role.getRoleId();
        }
        logger.warn("{} 已存在", role.getRole());
        return dbRole.getRoleId();
    }

    @Override
    public Role getByRole(String role) {
        return roleMapper.getByRole(role, DeletedEnum.NOT_DELETED.getCode());
    }

    @Override
    public List<Role> findByUserInfoId(Long userInfoId) {
        return roleMapper.findByUserInfoId(userInfoId, DeletedEnum.NOT_DELETED.getCode());
    }
}
