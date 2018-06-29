package com.voidforce.activiti.common.bean;

import java.util.HashMap;

public class HashMapResult extends HashMap<Object, Object> {
    private static final String SUCCESS = "success";

    private static final String MESSAGE = "message";

    private static final String DATA = "data";

    public HashMapResult() {
        super();
        this.put(SUCCESS, true);
    }

    public void success(Boolean isSuccess, String message, Object data) {
        this.put(SUCCESS, isSuccess);
        this.put(MESSAGE, message);
        this.put(DATA, data);
    }

    public static HashMapResult success() {
        return new HashMapResult();
    }

    public static HashMapResult success(String message) {
        HashMapResult result = new HashMapResult();
        result.success(true, message, null);
        return result;
    }

    public static HashMapResult success(String message, Object data) {
        HashMapResult result = new HashMapResult();
        result.success(true, message, data);
        return result;
    }

    public static HashMapResult failure(String message) {
        HashMapResult result = new HashMapResult();
        result.success(false, message, null);
        return result;
    }

    public static HashMapResult failure(String message, Object data) {
        HashMapResult result = new HashMapResult();
        result.success(false, message, data);
        return result;
    }

    public Boolean isSuccess() {
        return Boolean.valueOf(this.getOrDefault(SUCCESS, true).toString());
    }

    public Boolean isFailure() {
        return !this.isSuccess();
    }

    public String getMessage() {
        return this.getOrDefault(MESSAGE, "").toString();
    }

    public Object getData() {
        return this.getOrDefault(DATA, null);
    }
}
