package com.voidforce.activiti.service;

import com.voidforce.activiti.common.bean.HashMapResult;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Transactional
public class SimpleTaskService {
	private static final Logger logger = LoggerFactory.getLogger(SimpleTaskService.class);

	@Autowired
	private TaskService taskService;

	public HashMapResult complete(String processInstanceId, String comment, String expression) {
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		if(task == null) {
			logger.error("process {} task is not exists", processInstanceId);
			return HashMapResult.failure("流程任务不存在");
		}
		if(StringUtils.isNotEmpty(comment)) {
			taskService.addComment(task.getId(), processInstanceId, comment);
		}
		Map<String, Object> variables = new HashMap<>();
		variables.put("expression", expression);

		taskService.complete(task.getId(), variables);

		task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		HashMapResult result = HashMapResult.success();
		result.put("isFinished", task == null);
		return result;
	}

	//	SELECT DISTINCT RES.* FROM activiti.ACT_RU_TASK RES
	//  LEFT JOIN activiti.ACT_RU_IDENTITYLINK I_OR0 ON I_OR0.TASK_ID_ = RES.ID_
	//  INNER JOIN activiti.ACT_RE_PROCDEF D ON RES.PROC_DEF_ID_ = D.ID_
	//  WHERE
	//  FIND_IN_SET(D.KEY_, #{processDefinitionIds})
	//  AND (
	//  RES.ASSIGNEE_ = #{userId}
	//  OR RES.OWNER_ = #{userId}
	//  OR ( RES.ASSIGNEE_ IS NULL AND I_OR0.TYPE_ = 'candidate' AND ( I_OR0.USER_ID_ = #{userId} OR I_OR0.GROUP_ID_ IN (#{groupId}) )  )
	//  ) ORDER BY RES.ID_ ASC
	public List<Task> findBy(String userId, String groupId, List<String> processDefinitionIds) {
        List<Task> taskList = new ArrayList<>();

		if(StringUtils.isEmpty(userId) && StringUtils.isEmpty(groupId) && CollectionUtils.isEmpty(processDefinitionIds)) {
			logger.error("Wrong params");
			return taskList;
		}

		if(StringUtils.isNotEmpty(userId)) {
			StringBuilder sql = new StringBuilder("SELECT DISTINCT RES.* FROM activiti.ACT_RU_TASK RES " +
					" LEFT JOIN activiti.ACT_RU_IDENTITYLINK I_OR0 ON I_OR0.TASK_ID_ = RES.ID_ " +
					" INNER JOIN activiti.ACT_RE_PROCDEF D ON RES.PROC_DEF_ID_ = D.ID_ " +
					" WHERE "
			);

			if(!CollectionUtils.isEmpty(processDefinitionIds)) {
				sql.append(" FIND_IN_SET(D.KEY_, #{processDefinitionIds}) AND ");
			}

			sql.append(" ( " +
					" RES.ASSIGNEE_ = #{userId} " +
					" OR RES.OWNER_ = #{userId} ");

			if(StringUtils.isNotEmpty(groupId)) {
				sql.append(" OR ( RES.ASSIGNEE_ IS NULL AND I_OR0.TYPE_ = 'candidate' AND ( I_OR0.USER_ID_ = #{userId} OR I_OR0.GROUP_ID_ IN (#{groupId}) )  ) ");
			} else {
				sql.append(" OR ( RES.ASSIGNEE_ IS NULL AND I_OR0.TYPE_ = 'candidate' AND I_OR0.USER_ID_ = #{userId} ) ");
			}

			sql.append(" ) ORDER BY RES.ID_ ASC ");


			taskList = taskService.createNativeTaskQuery()
					.sql(sql.toString())
					.parameter("userId", userId)
					.parameter("groupId", groupId)
					.parameter("processDefinitionIds", CollectionUtils.isEmpty(processDefinitionIds) ? "" : StringUtils.join(processDefinitionIds, ","))
					.list();
		} else {
		    if(StringUtils.isNotEmpty(groupId) && CollectionUtils.isEmpty(processDefinitionIds)) {
                taskList = taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList(groupId)).list();
            }

			if(StringUtils.isNotEmpty(groupId) && !CollectionUtils.isEmpty(processDefinitionIds)) {
				taskList = taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList(groupId)).processDefinitionKeyIn(processDefinitionIds).list();
			}

            if(StringUtils.isEmpty(groupId) && !CollectionUtils.isEmpty(processDefinitionIds)) {
                taskList = taskService.createTaskQuery().processDefinitionKeyIn(processDefinitionIds).list();
            }
		}

		return taskList;
	}
}
