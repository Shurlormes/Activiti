package com.voidforce.activiti.controller;

import com.voidforce.activiti.bean.DepartmentRole;
import com.voidforce.activiti.bean.Role;
import com.voidforce.activiti.bean.UserInfo;
import com.voidforce.activiti.service.departmentRole.DepartmentRoleService;
import com.voidforce.activiti.service.role.RoleService;
import com.voidforce.activiti.service.userInfo.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentRoleService departmentRoleService;

    @GetMapping("/get")
    public String index(Model model) {

        UserInfo userByName = userInfoService.getByName("张三");

        Role role = roleService.getByName("ROLE_ADMIN");

        DepartmentRole departmentRole = new DepartmentRole();

        departmentRole.setDepartmentId(1L);

        List<DepartmentRole> departmentRoleList = departmentRoleService.findBy(departmentRole);

        List<String> number = new ArrayList<>();
        number.add("ROLE_ADMIN");
        number.add("ROLE_USER");

        model.addAttribute("number", number);

        return "index";
    }

}
