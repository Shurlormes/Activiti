package com.voidforce.activiti.config.security;

import com.voidforce.activiti.bean.Menu;
import com.voidforce.activiti.bean.Role;
import com.voidforce.activiti.bean.UserInfo;
import com.voidforce.activiti.service.menu.MenuService;
import com.voidforce.activiti.service.role.RoleService;
import com.voidforce.activiti.service.userInfo.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoService.getByUsername(name);
        if(userInfo == null) {
            logger.warn("用户 {} 不存在", name);
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        this.loadUserAuthorities(userInfo);

        return userInfo;
    }

    private void loadUserAuthorities(UserInfo userInfo) {
        List<Role> roleList = roleService.findByUserInfoId(userInfo.getUserInfoId());
        Set<Long> roleIds = new HashSet<>();

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        roleList.forEach(role -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(authority);
            roleIds.add(role.getRoleId());
        });


        if(!CollectionUtils.isEmpty(roleIds)) {
            List<Menu> menuList = menuService.findByRoleIds(StringUtils.join(roleIds, ","));
            userInfo.setMenus(menuList);
        }

        userInfo.setRoles(roleList);
        userInfo.setAuthorities(authorities);
    }
}
