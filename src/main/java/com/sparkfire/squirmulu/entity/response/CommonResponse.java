package com.sparkfire.squirmulu.entity.response;

public class CommonResponse<T> {
    private int status;
    private String message;
    private T data;

    // 构造函数、getter和setter方法


    public CommonResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public CommonResponse() {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> CommonResponse<T> success(T data) {
        CommonResponse<T> response = new CommonResponse<>();
        response.setStatus(200);
        response.setMessage("Success");
        response.setData(data);
        return response;
    }

    public static <T> CommonResponse<T> error(int status, String message) {
        CommonResponse<T> response = new CommonResponse<>();
        response.setStatus(status);
        response.setMessage(message);
        return response;
    }
}
