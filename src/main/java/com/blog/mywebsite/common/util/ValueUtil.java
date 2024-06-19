package com.blog.mywebsite.common.util;

import java.util.Objects;

public final class ValueUtil {
    private ValueUtil() {
        throw new AssertionError();
    }

    public static <T> void checkDataIsNull(T data, String message){
        if(Objects.isNull(data)){
            throw new IllegalArgumentException(message);
        }
    }
}
