package com.voidforce.activiti.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class CompensationTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("补偿任务被触发");
    }
}
