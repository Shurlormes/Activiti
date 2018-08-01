package com.voidforce.activiti.service.menu.impl;

import com.voidforce.activiti.bean.Menu;
import com.voidforce.activiti.common.constant.CommonConstant;
import com.voidforce.activiti.exception.BaseException;
import com.voidforce.activiti.mapper.menu.MenuMapper;
import com.voidforce.activiti.service.menu.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Long insert(Menu menu) {
        if(StringUtils.isNotEmpty(menu.getPath())) {
            Menu dbMenu = this.getByPath(menu.getPath());
            if(dbMenu != null) {
                logger.error("路径 {} 已存在", menu.getPath());
                throw new BaseException("路径 " + menu.getPath() + " 已存在");
            }
        }
        menu.setDeleted(CommonConstant.NOT_DELETED);
        menuMapper.insert(menu);
        return menu.getMenuId();
    }

    @Override
    public Menu getByPath(String path) {
        return menuMapper.getByPath(path, CommonConstant.NOT_DELETED);
    }

    @Override
    public List<Menu> findByRoleIds(String roleIds) {
        return menuMapper.findByRoleIds(roleIds, CommonConstant.NOT_DELETED);
    }

    @Override
    public List<Menu> findAll(Menu menu) {
        return menuMapper.findAll(menu);
    }

    @Override
    public void delete(Long menuId) {
        menuMapper.delete(menuId);
    }

    @Override
    public void update(Menu menu) {
        if(StringUtils.isNotEmpty(menu.getPath())) {
            Menu dbMenu = this.getByPath(menu.getPath());
            if(dbMenu != null && !menu.getMenuId().equals(dbMenu.getMenuId())) {
                logger.error("路径 {} 已存在", menu.getPath());
                throw new BaseException("路径 " + menu.getPath() + " 已存在");
            }
        }

        menuMapper.update(menu);
    }
}
