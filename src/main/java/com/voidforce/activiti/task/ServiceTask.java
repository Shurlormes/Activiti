package com.voidforce.activiti.task;

import org.activiti.engine.runtime.Execution;

import java.io.Serializable;

public class ServiceTask implements Serializable {

    private String name = "zsyh";


    public String getName() {
        return name;
    }

    public void print(Execution execution) {
        System.out.println(execution);
    }
}
