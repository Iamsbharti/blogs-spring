package com.blog.blogapi.common;

import com.blog.blogapi.dto.UserDTO;
import com.blog.blogapi.models.User;

public class DtoConvertor {
    public static User userDtoToEntity(UserDTO userDto){
        User user = new User();

        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        return user;
    }

    public static UserDTO userEntityToDto(User user){
        UserDTO userDto = new UserDTO();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        return userDto;
    }
}
