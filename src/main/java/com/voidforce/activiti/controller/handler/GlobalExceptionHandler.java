package com.voidforce.activiti.controller.handler;

import com.voidforce.activiti.common.bean.HashMapResult;
import com.voidforce.activiti.exception.BaseException;
import com.voidforce.activiti.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({BaseException.class})
	public String baseExceptionHandler(Exception e) {
		return JsonUtil.toJson(HashMapResult.failure(e.getMessage()));
	}

}
