package com.example.demo.util;

public class Result {
    // 结果标识：true 成功，false 失败
    private boolean flag = true;
    // 结果消息
    private String message;
    // 数据内容
    private Object data;

    public Result() {
    }

    public Result(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public Result(boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public Result flag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public Result data(Object data) {
        this.data = data;
        return this;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}