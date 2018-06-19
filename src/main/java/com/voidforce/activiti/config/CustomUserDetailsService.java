package com.voidforce.activiti.config;

import com.voidforce.activiti.bean.DepartmentRole;
import com.voidforce.activiti.bean.UserInfo;
import com.voidforce.activiti.service.departmentRole.DepartmentRoleService;
import com.voidforce.activiti.service.userInfo.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private DepartmentRoleService departmentRoleService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoService.getByName(name);
        if(userInfo == null) {
            logger.warn("用户 {} 不存在", name);
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        this.loadUserAuthorities(userInfo);

        return userInfo;
    }

    private void loadUserAuthorities(UserInfo userInfo) {
        DepartmentRole departmentRole = new DepartmentRole();
        departmentRole.setDepartmentId(userInfo.getDepartmentId());
        List<DepartmentRole> departmentRoleList = departmentRoleService.findBy(departmentRole);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        departmentRoleList.forEach(i -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(i.getRoleName());
            authorities.add(authority);
        });
        userInfo.setAuthorities(authorities);
    }
}
