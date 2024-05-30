package com.blog.mywebsite.api.response;

public class SuccessfulDataResponse<T> extends BaseResponse<T>{
    public SuccessfulDataResponse(int httpStatusCode, String message, T data) {
        super(true, httpStatusCode, message, data);
    }
}
