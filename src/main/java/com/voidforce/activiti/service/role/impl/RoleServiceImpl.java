package com.voidforce.activiti.service.role.impl;

import com.voidforce.activiti.bean.Role;
import com.voidforce.activiti.mapper.role.RoleMapper;
import com.voidforce.activiti.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Long insert(Role role) {
        Role dbRole = this.getByName(role.getName());
        if(dbRole != null) {
            roleMapper.insert(role);
            return role.getRoleId();
        }
        return dbRole.getRoleId();
    }

    @Override
    public Role getById(Long roleId) {
        return roleMapper.getById(roleId);
    }

    @Override
    public Role getByName(String name) {
        return roleMapper.getByName(name);
    }
}
