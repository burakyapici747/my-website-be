package com.blog.mywebsite.api.response;

public class SuccessfulResponse extends BaseResponse<Void>{
    public SuccessfulResponse(int httpStatus, String message) {
        super(true, httpStatus, message, null);
    }
}
