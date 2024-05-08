package com.blog.mywebsite.api.response;

public abstract class BaseResponse<T>{
    public final boolean isSuccess;
    public final int httpStatus;
    public final String message;
    public final T data;

    public BaseResponse(boolean isSuccess, int httpStatus, String message, T data){
        this.isSuccess = isSuccess;
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }
}