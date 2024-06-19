package com.blog.mywebsite.api.response;

public class SuccessfulResponse extends BaseResponse<Void>{
    public SuccessfulResponse(int httpStatusCode, String message) {
        super(true, httpStatusCode, message, null);
    }
}
