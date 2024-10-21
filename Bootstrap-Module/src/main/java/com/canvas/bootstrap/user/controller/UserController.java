package com.canvas.bootstrap.user.controller;

import com.canvas.bootstrap.user.api.UserApi;
import com.canvas.bootstrap.user.dto.RegisterUserRequest;
import com.canvas.bootstrap.user.dto.RegisterUserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserApi {
    @Override
    @PostMapping
    public RegisterUserResponse register(@RequestBody RegisterUserRequest request) {
        return new RegisterUserResponse("access", "response");
    }
}
