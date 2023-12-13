package com.blog.mywebsite.api.response;

public class DataResponse<T> extends BaseResponse{
    private T data;

    public DataResponse(T data, boolean isSuccess, String message) {
        super(isSuccess, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
