package com.blog.blogapi.repository;

import com.blog.blogapi.models.User;
import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User newUser;
    private User testUser;
    @BeforeEach
    void setUp() {
        log.info("Test SetUp");
        newUser = new User(189,"sb","sbdas@gmail.com","pwd12345");
    }

    @Test
    void saveUserTest(){
        testUser = userRepository.save(newUser);
        assertEquals(newUser.getEmail(),testUser.getEmail());
    }
    @Test
    void findUsersByEmailSuccess() {
        Optional<User> userFound = userRepository.findUsersByEmail("sb@gmail.com");
        assertTrue (userFound.isPresent());
    }
    @Test
    void findUserByEmailError(){
        Optional<User> userNF= userRepository.findUsersByEmail("dsgd@gmail.com");
        assertFalse(userNF.isPresent());
        deleteTestUserSuccess();
    }

    void deleteTestUserSuccess(){
        log.info("Test SetUp Destroy::");
        Optional<User> userByEmail = userRepository.findUsersByEmail("sbdas@gmail.com");
        userRepository.delete(userByEmail.get());
    }
}