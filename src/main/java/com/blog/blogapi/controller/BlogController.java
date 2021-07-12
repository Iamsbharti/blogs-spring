package com.blog.blogapi.controller;


import com.blog.blogapi.common.DtoConvertor;
import com.blog.blogapi.common.ResponseHandler;
import com.blog.blogapi.dto.UserLoginDTO;
import com.blog.blogapi.dto.UserRegistrationDTO;
import com.blog.blogapi.exception.ApiResponse;
import com.blog.blogapi.exception.LoginFailureException;
import com.blog.blogapi.exception.UserNotFoundException;
import com.blog.blogapi.models.User;
import com.blog.blogapi.services.UserServices;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api/v1/blogs")
public class BlogController implements WebMvcConfigurer {
    @Autowired
    private UserServices userServices;
    @Autowired
    private ResponseHandler responseHandler;

    @GetMapping("/ping")
    public String apiPingTest(){
        return "BLOG API PING TEST UP";
    }

    @PostMapping("/user/create")
    @ResponseBody
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDto) throws Exception {
        log.info("Register User Control::");

        User userObject = DtoConvertor.registerUserDtoToEntity(userRegistrationDto);
        User createdUser = userServices.createUserService(userObject);
        UserRegistrationDTO dtoUserObject = DtoConvertor.userEntityToDto(createdUser);

        ApiResponse registerUserResponse = new ApiResponse();
        registerUserResponse.setData(dtoUserObject);
        registerUserResponse.setMessage("User Registration Success");
        registerUserResponse.setStatus("success");

        log.info(registerUserResponse.getMessage());
        return new ResponseEntity<>(registerUserResponse,HttpStatus.OK);
    }

    @PostMapping("/user/login")
    @ResponseBody
    public ResponseEntity<ApiResponse> loginController(@Valid @RequestBody UserLoginDTO userLoginDTO) throws UserNotFoundException, LoginFailureException {
        log.info("Login User Control::");
        User userObject = DtoConvertor.loginUserDtoToEntity(userLoginDTO);
        Map loginResponse = userServices.loginServices(userObject);
        ApiResponse response = new ApiResponse("success", "Login Success",loginResponse);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }
}
