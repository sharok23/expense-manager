package com.edstem.expensemanager.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.expensemanager.contract.Request.LoginRequest;
import com.edstem.expensemanager.contract.Request.SignupRequest;
import com.edstem.expensemanager.contract.Response.LoginResponse;
import com.edstem.expensemanager.contract.Response.SignupResponse;
import com.edstem.expensemanager.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private UserService userService;

    @Test
    void testSignUp() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("testUser");
        signupRequest.setEmail("testUser@example.com");
        signupRequest.setPassword("testPassword");

        SignupResponse expectedResponse = new SignupResponse();
        expectedResponse.setUserId(1L);

        when(userService.signUp(any(SignupRequest.class))).thenReturn(expectedResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(signupRequest);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/user/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));

        verify(userService, times(1)).signUp(any(SignupRequest.class));
    }

    @Test
    void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("testusers@example.com");
        loginRequest.setPassword("password");

        LoginResponse expectedResponse = new LoginResponse();
        expectedResponse.setUserId(1L);
        expectedResponse.setName("Test");

        when(userService.login(any(LoginRequest.class))).thenReturn(expectedResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(loginRequest);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }
}
