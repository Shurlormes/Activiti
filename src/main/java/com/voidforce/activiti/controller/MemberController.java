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
        HashMapResult result = HashMapResult.success(null, userInfoPageInfo);
        return JsonUtil.convertObject2Json(result);
    }

    @DeleteMapping("/{userInfoId}")
    public String delete(@PathVariable Long userInfoId) {
        userInfoService.delete(userInfoId);
        return JsonUtil.convertObject2Json(HashMapResult.success());
    }

}
