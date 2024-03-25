package com.blog.mywebsite.api.response;

public class ErrorResponse extends BaseResponse<Void>{
    public ErrorResponse(int httpStatus, String message) {
        super(false, httpStatus, message, null);
    }

}