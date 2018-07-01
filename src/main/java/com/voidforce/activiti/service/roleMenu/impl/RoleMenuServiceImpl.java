package com.voidforce.activiti.service.roleMenu.impl;

import com.voidforce.activiti.bean.RoleMenu;
import com.voidforce.activiti.common.enums.DeletedEnum;
import com.voidforce.activiti.mapper.roleMenu.RoleMenuMapper;
import com.voidforce.activiti.service.roleMenu.RoleMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleMenuServiceImpl implements RoleMenuService {
    private Logger logger = LoggerFactory.getLogger(RoleMenuServiceImpl.class);

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public Long insert(RoleMenu roleMenu) {
        RoleMenu dbRoleMenu = this.getByRoleIdAndMenuId(roleMenu.getRoleId(), roleMenu.getMenuId());
        if(dbRoleMenu == null) {
            roleMenuMapper.insert(roleMenu);
            return roleMenu.getRoleMenuId();
        }
        logger.warn("权限 {} 已存在 {} 菜单", roleMenu.getRoleId(), roleMenu.getMenuId());
        return dbRoleMenu.getRoleMenuId();
    }

    @Override
    public RoleMenu getByRoleIdAndMenuId(Long roleId, Long menuId) {
        return roleMenuMapper.getByRoleIdAndMenuId(roleId, menuId, DeletedEnum.NOT_DELETED.getCode());
    }

    @Override
    public void deleteBy(RoleMenu roleMenu) {
        roleMenuMapper.deleteBy(roleMenu);
    }
}
