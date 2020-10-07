package com.voidforce.activiti.service;

import com.voidforce.activiti.common.bean.HashMapResult;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Service
@Transactional
public class SimpleRuntimeService {
	private static final Logger logger = LoggerFactory.getLogger(SimpleRuntimeService.class);

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private TaskService taskService;

	public HashMapResult startProcess(String userId, String processDefinitionId, Map<String, Object> variables) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		if(processDefinition == null) {
			logger.error("process definition {} is not exists", processDefinitionId);
			return HashMapResult.failure("流程定义" + processDefinitionId + "不存在");
		}

		identityService.setAuthenticatedUserId(userId);
		ProcessInstance processInstance;
		if(!CollectionUtils.isEmpty(variables)) {
			processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
		} else {
			processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
		}

		HashMapResult result = HashMapResult.success();
		result.put("processInstanceId", processInstance.getId());
		return result;
	}


	public HashMapResult cancel(String processInstanceId, String reason) {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if(processInstance == null) {
			logger.error("process instance {} is not exists", processInstanceId);
			return HashMapResult.failure("流程实例不存在");
		}

		runtimeService.deleteProcessInstance(processInstanceId, reason);
		return HashMapResult.success();
	}
}
