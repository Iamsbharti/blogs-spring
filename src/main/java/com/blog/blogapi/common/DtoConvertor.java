package com.blog.blogapi.common;

import com.blog.blogapi.dto.UserLoginDTO;
import com.blog.blogapi.dto.UserRegistrationDTO;
import com.blog.blogapi.models.User;

public class DtoConvertor  {
    public static User registerUserDtoToEntity(UserRegistrationDTO userRegistrationDto){
        User user = new User();

        user.setId(userRegistrationDto.getId());
        user.setName(userRegistrationDto.getName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(userRegistrationDto.getPassword());

        return user;
    }

    public static UserRegistrationDTO userEntityToDto(User user){
        UserRegistrationDTO userRegistrationDto = new UserRegistrationDTO();

        userRegistrationDto.setId(user.getId());
        userRegistrationDto.setName(user.getName());
        userRegistrationDto.setEmail(user.getEmail());
        userRegistrationDto.setPassword(user.getPassword());

        return userRegistrationDto;
    }

    public static User loginUserDtoToEntity(UserLoginDTO userLoginDTO) {
        User user = new User();

        user.setEmail(userLoginDTO.getEmail());
        user.setPassword(userLoginDTO.getPassword());

        return user;
    }
}
