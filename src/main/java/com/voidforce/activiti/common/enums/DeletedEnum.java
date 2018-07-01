package com.voidforce.activiti.common.enums;

public enum DeletedEnum {
    NOT_DELETED(0, "未删除"), DELETED(1, "已删除");

    private Integer code;

    private String name;

    DeletedEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
