package com.voidforce.activiti.common.bean;

import java.util.HashMap;

public class HashMapResult extends HashMap<Object, Object> {
    private static final String SUCCESS = "success";

    private static final String MESSAGE = "message";

    public HashMapResult() {
        super();
        this.put(SUCCESS, true);
    }

    public HashMapResult(Boolean isSuccess, String message) {
        super();
        this.put(SUCCESS, isSuccess);
        this.put(MESSAGE, message);
    }

    public static HashMapResult success() {
        return new HashMapResult();
    }

    public static HashMapResult success(String message) {
        return new HashMapResult(true, message);
    }

    public static HashMapResult failure(String message) {
        return new HashMapResult(false, message);
    }

    public Boolean isSuccess() {
        return Boolean.valueOf(this.get(SUCCESS).toString());
    }

    public Boolean isFailure() {
        return !this.isSuccess();
    }

    public String getMessage() {
        return this.getOrDefault(MESSAGE, "").toString();
    }

    public void setMessage(String message) {
        this.put(MESSAGE, message);
    }
}
