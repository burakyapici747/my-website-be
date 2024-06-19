package com.blog.mywebsite.api.response;

public abstract class BaseResponse<T>{
    public final boolean isSuccess;
    public final int httpStatusCode;
    public final String message;
    public final T data;

    protected BaseResponse(boolean isSuccess, int httpStatus, String message, T data){
        this.isSuccess = isSuccess;
        this.httpStatusCode = httpStatus;
        this.message = message;
        this.data = data;
    }
}