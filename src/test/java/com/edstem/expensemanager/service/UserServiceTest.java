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
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("testUser");
        signupRequest.setEmail("testUser@example.com");
        signupRequest.setPassword("testPassword");

        User user =
                User.builder()
                        .id(1L)
                        .name("testUser")
                        .email("testUser@example.com")
                        .hashedPassword("hashedPassword")
                        .build();

        when(userRepository.existsByEmail("testUser@example.com")).thenReturn(false);
        when(passwordEncoder.encode("testPassword")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        SignupResponse response = userService.signUp(signupRequest);

        assertEquals(1L, response.getUserId());
        verify(userRepository, times(1)).existsByEmail("testUser@example.com");
        verify(passwordEncoder, times(1)).encode("testPassword");
        verify(userRepository, times(1)).save(any(User.class));
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
