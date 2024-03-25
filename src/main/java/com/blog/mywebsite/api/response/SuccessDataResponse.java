package com.blog.mywebsite.api.response;

public class SuccessDataResponse<T> extends BaseResponse<T>{
    public SuccessDataResponse(int httpStatus, String message, T data) {
        super(true, httpStatus, message, data);
    }
}
