package com.voidforce.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.el.FixedValue;

import java.io.Serializable;

public class UserTaskListener implements Serializable {

    private FixedValue createVariableTest;
    private FixedValue assignmentVariableTest;
    private FixedValue completeVariableTest;

    public void create(DelegateTask task) {
        System.out.println("任务创建时监听: " + task);
        System.out.println("任务创建时监听：" + task.getAssignee());
        System.out.println("任务创建时监听：" + createVariableTest);
    }

    public void assignment(DelegateTask task) {
        System.out.println("指定代理人时监听：" + task);
        System.out.println("指定代理人时监听：" + task.getAssignee());
        System.out.println("指定代理人时监听：" + assignmentVariableTest);
    }

    public void complete(DelegateTask task) {
        System.out.println("任务完成时监听：" + task);
        System.out.println("任务完成时监听：" + task.getAssignee());
        System.out.println("任务完成时监听：" + completeVariableTest);
    }

    public void setCreateVariableTest(FixedValue createVariableTest) {
        this.createVariableTest = createVariableTest;
    }

    public void setAssignmentVariableTest(FixedValue assignmentVariableTest) {
        this.assignmentVariableTest = assignmentVariableTest;
    }

    public void setCompleteVariableTest(FixedValue completeVariableTest) {
        this.completeVariableTest = completeVariableTest;
    }
}
