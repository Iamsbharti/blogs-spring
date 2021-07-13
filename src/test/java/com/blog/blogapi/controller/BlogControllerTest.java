package com.blog.blogapi.controller;

import com.blog.blogapi.common.DtoConvertor;
import com.blog.blogapi.dto.UserLoginDTO;
import com.blog.blogapi.dto.UserRegistrationDTO;
import com.blog.blogapi.exception.ApiResponse;
import com.blog.blogapi.exception.LoginFailureException;
import com.blog.blogapi.exception.UserNotFoundException;
import com.blog.blogapi.models.User;
import com.blog.blogapi.repository.UserRepository;
import com.blog.blogapi.services.UserServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.jdbc.support.incrementer.HanaSequenceMaxValueIncrementer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.print.attribute.standard.Media;
import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.client.match.JsonPathRequestMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.mockito.Mock;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServices userMockServices;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper mapper;

    @Mock
    private BlogController blogController;

    private static String url ="/api/v1/blogs";
    private User newUser;
    private UserLoginDTO loginUser;
    private Map<String,String> tokenMap = new HashMap<>();
    private static final String REGISTER_URL= url+"/user/create";
    private static final String LOGIN_URL= url+"/user/login";

    @BeforeEach
    void setUp() {
        newUser = new User(189,"sb","sbdas@gmail.com","pwd12345");
        loginUser= new UserLoginDTO("sbdas@gmail.com","pwd12348");
        tokenMap.put("token","fuywetr672r23dysgah");
    }
    private MockHttpServletRequestBuilder getMockHttpServletRequestBuilder(String url,Object content) throws JsonProcessingException {
        return MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getWriteValueAsString(content));
    }

    private String getWriteValueAsString(Object content) throws JsonProcessingException {
        return this.mapper.writeValueAsString(content);
    }

    private
    @AfterEach
    void destroy(){
        newUser = null;
    }
    @Test
    void apiPingTest() throws Exception{
        this.mockMvc.perform(get(url+"/ping"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("BLOG API PING TEST UP")));
    }

    @Test
    void registerUserSuccess() throws Exception {
        // mock user services for success
        when(userMockServices.createUserService(Mockito.any(User.class))).thenReturn(newUser);
        ApiResponse testResponse = new ApiResponse("success","User Registration Success",newUser);

        // create request api builder
        MockHttpServletRequestBuilder builder = getMockHttpServletRequestBuilder(REGISTER_URL,newUser);

        // assert/validate response
        this.mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(getWriteValueAsString(testResponse)));
    }


    @Test()
    void registerUserError() throws Exception{
        // compute MethodArgument required exception parameters i.e bindingResults & MethodParameter
        BeanPropertyBindingResult bindingResults= new BeanPropertyBindingResult(new UserRegistrationDTO(),"user");
        bindingResults.rejectValue("password","size must be between 8 and 16");
        MethodParameter methodParameters = new MethodParameter(new UserRegistrationDTO().getClass().getMethod("getPassword"), -1);
        Map map = new HashMap();
        map.put("password","size must be between 8 and 16");

        //mockito config
        when(userMockServices.createUserService(Mockito.any(User.class))).thenAnswer(inv-> {throw new MethodArgumentNotValidException(methodParameters,bindingResults);});
        ApiResponse failRegisterUserRes = new ApiResponse("error","400 BAD_REQUEST",map);

        // api - request builder
        User errorRegisterUser = new User(11,"test","sb@fdhsfg.com","dfer");
        MockHttpServletRequestBuilder  builder = getMockHttpServletRequestBuilder(REGISTER_URL,errorRegisterUser);

        // assert
        this.mockMvc.perform(builder)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(getWriteValueAsString(failRegisterUserRes)));
    }
    @Test
    void loginControllerSuccess() throws Exception, LoginFailureException {
        when(userMockServices.loginServices(Mockito.any(User.class))).thenReturn(tokenMap);
        ApiResponse testLoginResponse = new ApiResponse("success","Login Success", tokenMap);

        MockHttpServletRequestBuilder builder = getMockHttpServletRequestBuilder(LOGIN_URL,newUser);
        this.mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(getWriteValueAsString(testLoginResponse)));
    }
    @Test
    void loginControllerError_invalidCredentials() throws Exception,UserNotFoundException,LoginFailureException{
        when(userRepository.findUsersByEmail(newUser.getEmail())).thenReturn(Optional.ofNullable(newUser));
        when(userMockServices.loginServices(Mockito.any(User.class))).thenThrow(new LoginFailureException("Credential Mismatch"));
        ApiResponse testLoginFailureResponse = new ApiResponse("error","Credential Mismatch",null);

        MockHttpServletRequestBuilder builder = getMockHttpServletRequestBuilder(LOGIN_URL,newUser);

        this.mockMvc.perform(builder)
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(getWriteValueAsString(testLoginFailureResponse)));
    }
}