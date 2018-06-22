package com.voidforce.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTaskClassListener implements TaskListener {

	private Logger logger = LoggerFactory.getLogger(UserTaskClassListener.class);

	private FixedValue value;

	@Override
	public void notify(DelegateTask delegateTask) {
		logger.info("TaskListener >>>> DelegateTask：{}, value = {}", delegateTask, value.getValue(delegateTask));
	}

	public FixedValue getValue() {
		return value;
	}

	public void setValue(FixedValue value) {
		this.value = value;
	}
}
