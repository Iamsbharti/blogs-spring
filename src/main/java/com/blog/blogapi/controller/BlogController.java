package com.blog.blogapi.controller;


import com.blog.blogapi.common.DtoConvertor;
import com.blog.blogapi.common.ResponseHandler;
import com.blog.blogapi.dto.UserDTO;
import com.blog.blogapi.exception.ApiResponse;
import com.blog.blogapi.exception.LoginFailureException;
import com.blog.blogapi.exception.UserNotFoundException;
import com.blog.blogapi.models.User;
import com.blog.blogapi.services.UserServices;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDto) throws Exception {
        log.info("Register User Control::");

        User userObject = DtoConvertor.userDtoToEntity(userDto);
        User createdUser = userServices.createUserService(userObject);
        UserDTO dtoUserObject = DtoConvertor.userEntityToDto(createdUser);

        ApiResponse registerUserResponse = new ApiResponse();
        registerUserResponse.setData(dtoUserObject);
        registerUserResponse.setMessage("User Registration Success");
        registerUserResponse.setStatus("success");
        log.info(registerUserResponse.getMessage());
        return new ResponseEntity<>(registerUserResponse,HttpStatus.OK);
    }

    @PostMapping("/user/login")
    @ResponseBody
    public ResponseEntity<ApiResponse> loginController(@RequestBody User user) throws UserNotFoundException, LoginFailureException {
        log.info("Login User Control");
        Map loginResponse = userServices.loginServices(user);
        ApiResponse response = new ApiResponse("success", "Login Success",loginResponse);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }
}
