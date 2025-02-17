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

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "api/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private void checkData(String str, String message) {
        if (!str.matches("^[a-z0-9_]+$")) {
            throw new IllegalArgumentException("Invalid username format. Use only lowercase letters, numbers, and underscores." + message);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody UserRequest request) throws Exception {

        // Ensure userService.create() returns a valid UserEntity or UserResponse
        UserEntity user = this.userService.create(request);

        // Handle null address properly
        UserResponse response = UserResponse.fromUserEntity(user);

        return ResponseEntity.ok(new ApiResponse("Success! A new user has been created.", 200, response));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> findAll() {
        List<UserResponse> responses = this.userService.findAll()
                .stream()
                .map(UserResponse::fromUserEntity)
                .toList();
        return ResponseEntity.ok(new ApiResponse("Successfully retrieved all users", 200, responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findOne(@PathVariable("id") String idStr) {
        UserEntity user = this.userService.findOne(idStr);
        return ResponseEntity.ok(new ApiResponse("Successfully fetched user details", 200, UserResponse.fromUserEntity(user)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable("id") String idStr, @RequestBody UserRequest request) throws Exception {
        UserEntity user = this.userService.update(idStr, request);
        return ResponseEntity.ok(new ApiResponse("User details updated successfully", 200, UserResponse.fromUserEntity(user)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") String idStr) throws Exception {
        UserEntity user = this.userService.delete(idStr);
        return ResponseEntity.ok(new ApiResponse("User deleted successfully", 200, new HashMap<>()));
    }


}
