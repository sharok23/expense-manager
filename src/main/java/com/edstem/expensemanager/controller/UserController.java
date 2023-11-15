package com.edstem.expensemanager.controller;

import com.edstem.expensemanager.contract.Request.LoginRequest;
import com.edstem.expensemanager.contract.Request.SignupRequest;
import com.edstem.expensemanager.contract.Response.SignupResponse;
import com.edstem.expensemanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/v1/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public SignupResponse signUp(@Valid @RequestBody SignupRequest request) {
        return userService.signUp(request);
    }

    @PostMapping("/login")
    public Long login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
