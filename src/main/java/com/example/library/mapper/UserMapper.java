package com.example.library.mapper;

import com.example.library.dto.request.UserRegisterRequestDto;
import com.example.library.dto.response.UserRegisterResponseDto;
import com.example.library.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRegisterRequestDto userRegisterRequestDto);

    UserRegisterResponseDto toUserRegisterResponseDto(User user, String message);
}
