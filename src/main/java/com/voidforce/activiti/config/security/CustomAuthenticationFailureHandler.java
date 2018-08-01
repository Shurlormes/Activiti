package com.voidforce.activiti.config.security;

import com.voidforce.activiti.common.bean.HashMapResult;
import com.voidforce.activiti.util.JsonUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException {

        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        HashMapResult result;
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            result = HashMapResult.failure("用户名或密码输入错误，登录失败!");
        } else if (e instanceof DisabledException) {
            result = HashMapResult.failure("账户被禁用，登录失败，请联系管理员!");
        } else {
            result = HashMapResult.failure("登录失败!");
        }
        out.write(JsonUtil.toJson(result));
        out.flush();
        out.close();
    }
}
