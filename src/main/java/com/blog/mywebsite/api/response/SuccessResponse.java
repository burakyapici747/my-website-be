package com.blog.mywebsite.api.response;

public class SuccessResponse extends BaseResponse<Void>{
    public SuccessResponse(int httpStatus, String message) {
        super(true, httpStatus, message, null);
    }
}
