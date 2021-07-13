package com.blog.blogapi.exception;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String invalid_auth_token) {
        super(invalid_auth_token);
    }
}
