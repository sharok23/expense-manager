package com.edstem.expensemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.expensemanager.contract.Request.LoginRequest;
import com.edstem.expensemanager.contract.Request.SignupRequest;
import com.edstem.expensemanager.contract.Response.LoginResponse;
import com.edstem.expensemanager.contract.Response.SignupResponse;
import com.edstem.expensemanager.model.User;
import com.edstem.expensemanager.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

    @InjectMocks private UserService userService;

    @Mock private UserRepository userRepository;

    @Mock private ModelMapper modelMapper;

    @Mock private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignUp() {
        when(userRepository.existsByEmail("testuser@example.com")).thenReturn(false);

        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");

        User savedUser =
                User.builder()
                        .id(1L)
                        .name("TestUser")
                        .email("testuser@example.com")
                        .hashedPassword("hashedPassword")
                        .build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        SignupResponse expectedResponse = new SignupResponse();
        when(modelMapper.map(savedUser, SignupResponse.class)).thenReturn(expectedResponse);

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("TestUser");
        signupRequest.setEmail("testuser@example.com");
        signupRequest.setPassword("password");

        SignupResponse response = userService.signUp(signupRequest);

        assertEquals(expectedResponse, response);

        verify(userRepository, times(1)).save(any(User.class));
        verify(modelMapper, times(1)).map(savedUser, SignupResponse.class);
    }

    @Test
    void testLogin() {
        when(userRepository.existsByEmail("existingUser@example.com")).thenReturn(true);

        when(passwordEncoder.matches("correctPassword", "hashedPassword")).thenReturn(true);

        User existingUser =
                User.builder()
                        .id(1L)
                        .email("existingUser@example.com")
                        .hashedPassword("hashedPassword")
                        .build();
        when(userRepository.findByEmail("existingUser@example.com"))
                .thenReturn(Optional.of(existingUser));

        LoginRequest correctCredentials = new LoginRequest();
        correctCredentials.setEmail("existingUser@example.com");
        correctCredentials.setPassword("correctPassword");

        LoginResponse response = userService.login(correctCredentials);
        assertEquals(1L, response.getUserId());
    }
}
