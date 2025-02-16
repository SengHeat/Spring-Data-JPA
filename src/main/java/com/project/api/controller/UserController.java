package com.project.api.controller;

import com.project.api.model.base.ApiResponse;
import com.project.api.model.entity.UserEntity;
import com.project.api.model.request.UserRequest;
import com.project.api.model.response.UserResponse;
import com.project.api.repository.UserRepository;
import com.project.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody UserRequest request) throws Exception {

        // Ensure userService.create() returns a valid UserEntity or UserResponse
        UserEntity user = this.userService.create(request);

        // Handle null address properly
        UserResponse response = UserResponse.fromUserEntity(user);

        return ResponseEntity.ok(new ApiResponse("User created successfully", 200, response));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> findAll() {
        List<UserResponse> responses = this.userService.findAll()
                .stream()
                .map(UserResponse::fromUserEntity)
                .toList();
        return ResponseEntity.ok(new ApiResponse("all user", 200, responses));
    }
}
