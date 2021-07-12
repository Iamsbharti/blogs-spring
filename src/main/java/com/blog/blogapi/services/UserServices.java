package com.blog.blogapi.services;

import com.blog.blogapi.dto.UserDTO;
import com.blog.blogapi.exception.LoginFailureException;
import com.blog.blogapi.exception.UserNotFoundException;
import com.blog.blogapi.models.User;

import java.util.List;
import java.util.Map;

public interface UserServices {
    User createUserService(User user);

    List<User> getAllUsersService();

    Map<String,String> loginServices(User user) throws UserNotFoundException, LoginFailureException;
}
