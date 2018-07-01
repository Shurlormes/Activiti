package com.voidforce.activiti.service.menu.impl;

import com.voidforce.activiti.bean.Menu;
import com.voidforce.activiti.common.enums.DeletedEnum;
import com.voidforce.activiti.mapper.menu.MenuMapper;
import com.voidforce.activiti.service.menu.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    private Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Long insert(Menu menu) {
        Menu dbMenu = this.getByPath(menu.getPath());
        if(dbMenu == null) {
            menuMapper.insert(menu);
            return menu.getMenuId();
        }
        logger.warn("路径 {} 已存在", menu.getPath());
        return menu.getMenuId();
    }

    @Override
    public Menu getByPath(String path) {
        return menuMapper.getByPath(path, DeletedEnum.NOT_DELETED.getCode());
    }

    @Override
    public List<Menu> findByRoleIds(String roleIds) {
        return menuMapper.findByRoleIds(roleIds, DeletedEnum.NOT_DELETED.getCode());
    }
}
