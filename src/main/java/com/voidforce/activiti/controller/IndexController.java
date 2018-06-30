package com.voidforce.activiti.controller;

import com.voidforce.activiti.bean.UserInfo;
import com.voidforce.activiti.common.bean.HashMapResult;
import com.voidforce.activiti.service.departmentRole.DepartmentRoleService;
import com.voidforce.activiti.service.role.RoleService;
import com.voidforce.activiti.service.userInfo.UserInfoService;
import com.voidforce.activiti.util.JsonUtil;
import com.voidforce.activiti.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentRoleService departmentRoleService;

    @GetMapping("/")
    public String index() {
        UserInfo userInfo = SessionUtil.currentUserDeatils();
        HashMapResult result;
        if(userInfo == null) {
            result = HashMapResult.failure(null);
        } else {
            result = HashMapResult.success(null, userInfo);
        }
        return JsonUtil.convertObject2Json(result);
    }

}
