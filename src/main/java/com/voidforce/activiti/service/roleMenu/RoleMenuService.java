package com.voidforce.activiti.service.roleMenu;

import com.voidforce.activiti.bean.RoleMenu;

public interface RoleMenuService {

    Long insert(RoleMenu roleMenu);

    RoleMenu getByRoleIdAndMenuId(Long roleId, Long menuId);

    void deleteBy(RoleMenu roleMenu);

}
