package com.voidforce.activiti.config.security;

import com.voidforce.activiti.common.bean.HashMapResult;
import com.voidforce.activiti.util.JsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	public void commence(HttpServletRequest request, HttpServletResponse response,
	                     AuthenticationException arg2) throws IOException {
		
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		PrintWriter out = response.getWriter();
		HashMapResult result = HashMapResult.failure("你没有当前操作的权限");
		out.write(JsonUtil.toJson(result));
		out.flush();
		out.close();
	}
}
