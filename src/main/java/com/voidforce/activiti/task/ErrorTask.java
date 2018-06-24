package com.voidforce.activiti.task;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ErrorTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("错误服务任务：" + execution);
        throw new BpmnError("错误服务任务");
    }
}
