package com.example.library.controller;

import com.example.library.dto.request.UserRegisterRequestDto;
import com.example.library.dto.response.UserRegisterResponseDto;
import com.example.library.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User controller")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Registration a new user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegisterResponseDto registerUser(@RequestBody @Valid UserRegisterRequestDto userDto) {
        return userService.registration(userDto);
    }
}
