package com.blog.blogapi.exception;

public class LoginFailureException extends Throwable {
    public LoginFailureException(String credential_mismatch) {
        super(credential_mismatch);
    }
}
