package com.voidforce.activiti.service.activiti;

import com.voidforce.activiti.bean.activiti.HistoryTask;
import com.voidforce.activiti.common.bean.HashMapResult;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Comment;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SimpleHistoryService {
	private Logger logger = LoggerFactory.getLogger(SimpleHistoryService.class);

	@Autowired
	private HistoryService historyService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private IdentityService identityService;

	public HashMapResult queryHistoryTaskByStartUserId(String startUserId) {
		HashMapResult result = new HashMapResult();
		List<HistoricProcessInstance> historicProcessInstanceList
			= historyService.createHistoricProcessInstanceQuery().startedBy(startUserId).list();
		List<HistoryTask> historyTaskList = new ArrayList<>();
		for (HistoricProcessInstance historicProcessInstance : historicProcessInstanceList) {
			List<HistoricTaskInstance> historicTaskInstance
				= historyService.createHistoricTaskInstanceQuery().processInstanceId(historicProcessInstance.getId()).list();

			List<HistoryTask> temp = this.generateHistoryTask(historicTaskInstance);

			historyTaskList.addAll(temp);
		}
		result.put("historyTaskList", historyTaskList);
		return result;
	}

	public HashMapResult queryHistoryTaskByActProcInstId(String actProcInstId) {
		HashMapResult result = new HashMapResult();
		List<HistoricTaskInstance> historicTaskInstance = historyService.createHistoricTaskInstanceQuery().processInstanceId(actProcInstId).list();
		List<HistoryTask> historyTaskList = this.generateHistoryTask(historicTaskInstance);
		result.put("historyTaskList", historyTaskList);
		return result;
	}

	private List<HistoryTask> generateHistoryTask(List<HistoricTaskInstance> historicTaskInstance) {
		List<HistoryTask> historyTaskList = new ArrayList<>();
		for (HistoricTaskInstance taskInstance : historicTaskInstance) {
			HistoryTask historyTask = new HistoryTask();
			historyTask.setId(taskInstance.getId());
			historyTask.setName(taskInstance.getName());
			historyTask.setActProcInstId(taskInstance.getProcessInstanceId());
			historyTask.setActReProcdefId(taskInstance.getProcessDefinitionId());
			historyTask.setStartTime(taskInstance.getStartTime());
			historyTask.setEndTime(taskInstance.getEndTime());
			historyTask.setDeleteReason(taskInstance.getDeleteReason());
			if(StringUtils.isNotEmpty(taskInstance.getOwner())) {
				User user = identityService.createUserQuery().userId(taskInstance.getOwner()).singleResult();
				historyTask.setOwner(user.getFirstName());
			}
			List<Comment> comment = taskService.getTaskComments(taskInstance.getId());
			if(!CollectionUtils.isEmpty(comment)) {
				historyTask.setComment(comment.get(0).getFullMessage());
			}

			historyTaskList.add(historyTask);
		}

		return historyTaskList;
	}
}
