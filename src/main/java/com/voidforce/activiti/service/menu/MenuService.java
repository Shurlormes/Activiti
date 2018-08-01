package com.voidforce.activiti.service.menu;

import com.voidforce.activiti.bean.Menu;

import java.util.List;

public interface MenuService {

    Long insert(Menu menu);

    Menu getByPath(String path);

    List<Menu> findByRoleIds(String roleIds);

    List<Menu> findAll(Menu menu);

    void delete(Long menuId);

    void update(Menu menu);

}
