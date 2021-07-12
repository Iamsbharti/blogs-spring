package com.blog.blogapi.services;

import com.blog.blogapi.dto.UserDTO;
import com.blog.blogapi.exception.LoginFailureException;
import com.blog.blogapi.exception.UserNotFoundException;
import com.blog.blogapi.models.User;
import com.blog.blogapi.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
public class UserServicesImpl implements UserServices {
    private UserRepository userRepository;
    @Autowired
    public UserServicesImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public User createUserService(User user){
        log.info("Create User Service");
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsersService(){
        log.info("Get All User Service");
        return userRepository.findAll();
    }

    @Override
    public Map<String,String> loginServices(User user) throws UserNotFoundException, LoginFailureException {
        // check user's existence
        Optional<User> userExists = userRepository.findUsersByEmail(user.getEmail());
        if(!userExists.isPresent()){
            throw new UserNotFoundException("Resource Not found");
        }
        // validate credentials
        if(userExists.get().getPassword().equals(user.getPassword())){
            return Map.of("token","dhgfyeur723r5434BASFURDSYTF72635");
        }else{
            throw new LoginFailureException("Credential Mismatch");
        }
    }
}
