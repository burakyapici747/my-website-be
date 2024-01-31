package com.blog.mywebsite.api.response;

public class SuccessDataResponse<T> extends DataResponse<T>{

    public SuccessDataResponse(T data, String message){
        super(data, true, message);
    }
}
