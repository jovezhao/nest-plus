package com.zhaofujun.nest.demo.infrastructure.springboot;

public class ResultResponse {
    private int code;
    private String message;

    public ResultResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
