package com.voidforce.activiti.controller;

import com.voidforce.activiti.common.bean.HashMapResult;
import com.voidforce.activiti.util.JsonUtil;
import com.voidforce.activiti.util.SessionUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return JsonUtil.toJson(HashMapResult.success(null, SessionUtil.currentUserDeatils()));
    }

}
