package com.blog.blogapi.common;

import com.blog.blogapi.exception.ApiResponse;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler {
    public static ApiResponse computeResponse(String status, String message, Object data){
        return new ApiResponse(status,message,data);
    }
}
