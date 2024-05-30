package com.blog.mywebsite.api.response;


public class ErrorResponse extends BaseResponse<Void>{
    public ErrorResponse(int httpStatusCode, String message) {
        super(false, httpStatusCode, message, null);
    }
}