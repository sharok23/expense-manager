package com.edstem.expensemanager.service;

import com.edstem.expensemanager.contract.Request.LoginRequest;
import com.edstem.expensemanager.contract.Request.SignupRequest;
import com.edstem.expensemanager.contract.Response.LoginResponse;
import com.edstem.expensemanager.contract.Response.SignupResponse;
import com.edstem.expensemanager.model.User;
import com.edstem.expensemanager.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public SignupResponse signUp(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EntityExistsException("Invalid Signup");
        }
        User user =
                User.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .hashedPassword(passwordEncoder.encode(request.getPassword()))
                        .build();
        user = userRepository.save(user);
        return modelMapper.map(user, SignupResponse.class);
    }

    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        if (!userRepository.existsByEmail(email)) {
            throw new EntityNotFoundException("Invalid login");
        }
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        if (passwordEncoder.matches(password, user.getHashedPassword())) {
            LoginResponse response = new LoginResponse();
            response.setUserId(user.getId());
            return response;
        }
        throw new RuntimeException("Invalid login");
    }
}
