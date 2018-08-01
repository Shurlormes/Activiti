package com.voidforce.activiti.controller;

import com.github.pagehelper.PageInfo;
import com.voidforce.activiti.bean.UserInfo;
import com.voidforce.activiti.common.bean.HashMapResult;
import com.voidforce.activiti.common.constant.CommonConstant;
import com.voidforce.activiti.service.userInfo.UserInfoService;
import com.voidforce.activiti.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("")
    public String findAll(@RequestParam(required = false, defaultValue = CommonConstant.DEFAULT_PAGE_NUMBER) Integer page,
                          @RequestParam(required = false, defaultValue = CommonConstant.DEFAULT_PAGE_LIMIT) Integer limit,
                          @ModelAttribute UserInfo userInfo) {
        PageInfo<UserInfo> userInfoPageInfo = userInfoService.findAllForPage(page, limit, userInfo);
        return JsonUtil.toJson(HashMapResult.success(null, userInfoPageInfo));
    }

    @GetMapping("/{userInfoId}")
    public String getById(@PathVariable Long userInfoId) {
        UserInfo userInfo = userInfoService.getById(userInfoId);
        return JsonUtil.toJson(HashMapResult.success(null, userInfo));
    }

    @PostMapping("")
    public String insert(@ModelAttribute UserInfo userInfo) {
        userInfoService.insert(userInfo);
        return JsonUtil.toJson(HashMapResult.success());
    }

    @PutMapping("/{userInfoId}")
    public String update(@PathVariable Long userInfoId,
                         @ModelAttribute UserInfo userInfo) {
        userInfoService.update(userInfo);
        return JsonUtil.toJson(HashMapResult.success(null, userInfo));
    }

    @DeleteMapping("/{userInfoId}")
    public String delete(@PathVariable Long userInfoId) {
        userInfoService.delete(userInfoId);
        return JsonUtil.toJson(HashMapResult.success());
    }

}
