package com.blog.mywebsite.api.response;

public class SuccessResponse extends BaseResponse{
    public SuccessResponse(String message){
        super(true, message);
    }
}
