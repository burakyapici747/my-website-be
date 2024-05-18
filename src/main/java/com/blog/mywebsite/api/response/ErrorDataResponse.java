package com.blog.mywebsite.api.response;

public class ErrorDataResponse<T> extends BaseResponse<T> {
    public ErrorDataResponse(int httpStatus, String message, T data) {
        super(false, httpStatus, message, data);
    }
}