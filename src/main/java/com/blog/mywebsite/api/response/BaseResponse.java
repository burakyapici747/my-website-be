package com.blog.mywebsite.api.response;


public class BaseResponse<T>{
    private boolean isSuccess;
    private int httpStatus;
    private String message;
    private T data;

    public BaseResponse(boolean isSuccess, int httpStatus, String message, T data){
        this.isSuccess = isSuccess;
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
