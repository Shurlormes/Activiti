package com.voidforce.activiti.service;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SimpleHistoryService {
	private static final Logger logger = LoggerFactory.getLogger(SimpleHistoryService.class);

	@Autowired
	private HistoryService historyService;

	public List<HistoricProcessInstance> queryHistoryTaskByStartUserId(String startUserId) {
		return historyService.createHistoricProcessInstanceQuery().startedBy(startUserId).list();
	}

	public List<HistoricTaskInstance> queryHistoryTaskByProcessInstanceId(String processInstanceId) {
		return historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
	}

}
