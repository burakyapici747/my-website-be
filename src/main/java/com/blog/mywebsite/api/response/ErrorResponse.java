package com.blog.mywebsite.api.response;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    List<Error> errors
){
    public record Error(
            String id,
            Links links,
            String status,
            String code,
            String title,
            String detail,
            Source source,
            Meta meta
    ){
        public record Links(String self, String related){}
        public record Source(String pointer, String parameter){}
        public record Meta(String requestId, LocalDateTime timestamp, String version){}
    }
}