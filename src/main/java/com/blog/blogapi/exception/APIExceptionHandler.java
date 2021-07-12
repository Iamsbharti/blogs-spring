package com.blog.blogapi.exception;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class APIExceptionHandler{
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException exception){
       log.error("User Not Found Exception");
       ApiResponse response = new ApiResponse("error",exception.getMessage(),null);
       return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoginFailureException.class)
    public ResponseEntity<?> handleLoginFailure(LoginFailureException exception){
        log.error("credential Mismatch exception");
        ApiResponse response = new ApiResponse("error", exception.getMessage(),null);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception){
        log.error("Method Arg Exception");
        String errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList()).toString();

        Map<String,String> fieldErrors =new HashMap<String,String>();
        exception.getBindingResult().getFieldErrors().stream().forEach(err->{
            fieldErrors.put(err.getField(),err.getDefaultMessage());
        });
        ApiResponse response = new ApiResponse();
        response.setMessage(HttpStatus.BAD_REQUEST.toString());
        response.setStatus("error");
        response.setData(fieldErrors);

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
