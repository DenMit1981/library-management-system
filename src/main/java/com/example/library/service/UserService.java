package com.example.library.service;

import com.example.library.dto.request.UserRegisterRequestDto;
import com.example.library.dto.response.UserRegisterResponseDto;

public interface UserService {

    UserRegisterResponseDto registration(UserRegisterRequestDto userRegisterRequestDto);
}
