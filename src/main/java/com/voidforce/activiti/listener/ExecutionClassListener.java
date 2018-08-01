package com.voidforce.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutionClassListener implements ExecutionListener {
	private static final Logger logger = LoggerFactory.getLogger(ExecutionListener.class);

	private Expression fixedValue;

	private Expression dynamicValue;

	@Override
	public void notify(DelegateExecution delegateExecution) {
		logger.info("ExecutionListener >>>> DelegateExecution：{}", delegateExecution);
		logger.info("ExecutionListener >>>> fixedValue = {}, dynamicValue = {}",
			fixedValue.getValue(delegateExecution), dynamicValue.getValue(delegateExecution));
	}

	public Expression getFixedValue() {
		return fixedValue;
	}

	public void setFixedValue(Expression fixedValue) {
		this.fixedValue = fixedValue;
	}

	public Expression getDynamicValue() {
		return dynamicValue;
	}

	public void setDynamicValue(Expression dynamicValue) {
		this.dynamicValue = dynamicValue;
	}
}
