package com.voidforce.activiti.service.role;

import com.github.pagehelper.PageInfo;
import com.voidforce.activiti.bean.Role;

import java.util.List;

public interface RoleService {
    Long insert(Role role);

    Role getById(Long roleId);

    Role getByRole(String role);

    List<Role> findByUserInfoId(Long userInfoId);

    List<Role> findAll(Role role);

    PageInfo<Role> findAllForPage(Integer page, Integer limit, Role role);

    void update(Role role);

    void delete(Long roleId);
}
