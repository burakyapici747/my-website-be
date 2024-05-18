package com.blog.mywebsite.api.response;

public class SuccessfulDataResponse<T> extends BaseResponse<T>{
    public SuccessfulDataResponse(int httpStatus, String message, T data) {
        super(true, httpStatus, message, data);
    }
}
