package com.blog.blogapi.services;

import com.blog.blogapi.models.User;
import com.blog.blogapi.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServicesTest {

    @Autowired
    private UserServices userServices;

    @MockBean
    private UserRepository userRepository;

    private User newUser;
    @BeforeEach
    void setUp() {
         newUser = new User(189,"sb","sbdas@gmail.com","pwd12345");
    }

    @AfterEach
    void tearDown() {
        newUser = null;
    }

    @Test
    void createUserService() {
        when(userRepository.save(newUser)).thenReturn(newUser);
        assertEquals(newUser,userServices.createUserService(newUser));
    }

    @Test
    void getAllUsersService() {
        when(userRepository.findAll()).thenReturn(Stream.of(newUser).collect(Collectors.toList()));
        assertEquals(1,userServices.getAllUsersService().size());
    }

    @Test
    void loginServices() {
        when(userRepository.findUsersByEmail(newUser.getEmail())).thenReturn(java.util.Optional.ofNullable(newUser));
        User loginTestUser = userRepository.findUsersByEmail(newUser.getEmail()).get();
        assertEquals(loginTestUser.getPassword(),newUser.getPassword());
    }
}