package com.voidforce.activiti.service;

import org.activiti.bpmn.model.*;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;

@Service
@Transactional
public class SimpleRepositoryService {
	private Logger logger = LoggerFactory.getLogger(SimpleRepositoryService.class);

	@Autowired
	private RepositoryService repositoryService;


	public Deployment deployNewBpmn(String name){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()
				.createDeployment()
				.name(name)
				.addClasspathResource("deployment/"+name+".bpmn")
				.deploy();
        return deployment;
	}


	public UserTask createUserTask(String id, String name, String owner, String assignee, String candidateUsers, String candidateGroups) {
		UserTask userTask = new UserTask();
		userTask.setId("id-"+id);
		userTask.setName(name);
		if(StringUtils.isNotEmpty(owner)) {
			userTask.setOwner(owner);
		}
		if(StringUtils.isNotEmpty(assignee)) {
			userTask.setAssignee(assignee);
		}
		if(StringUtils.isNotEmpty(candidateUsers)) {
			userTask.setCandidateUsers(Arrays.asList(candidateUsers));
		}
		if(StringUtils.isNotEmpty(candidateGroups)) {
			userTask.setCandidateGroups(Arrays.asList(candidateGroups));
		}
		return userTask;
	}

	public SequenceFlow createSequenceFlow(String from, String to, String conditionExpression, String name) {
		SequenceFlow flow = new SequenceFlow();
		flow.setSourceRef(from);
		if(StringUtils.isNotEmpty(name)) {
			flow.setName(name);
		}
		if(StringUtils.isNotEmpty(conditionExpression)) {
			flow.setConditionExpression(conditionExpression);
		}
		flow.setTargetRef(to);
		return flow;
	}

	public StartEvent createStartEvent() {
		StartEvent startEvent = new StartEvent();
		startEvent.setId("id-"+UUID.randomUUID().toString());
		startEvent.setName("开始");
		return startEvent;
	}

	public EndEvent createEndEvent() {
		EndEvent endEvent = new EndEvent();
		endEvent.setId("id-"+UUID.randomUUID().toString());
		endEvent.setName("结束流程");
		return endEvent;
	}

	public ExclusiveGateway createExclusiveGateway() {
		ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
		exclusiveGateway.setId("id-"+UUID.randomUUID().toString());
		return exclusiveGateway;
	}

	public String getDefinitionIdByKey(String key){
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).singleResult();
		return processDefinition.getId();
	}
}
