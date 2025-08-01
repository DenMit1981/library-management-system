package com.example.library.mapper;

import com.example.library.dto.request.UserRegisterRequestDto;
import com.example.library.dto.response.UserRegisterResponseDto;
import com.example.library.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-30T09:02:05+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserRegisterRequestDto userRegisterRequestDto) {
        if ( userRegisterRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setName( userRegisterRequestDto.getName() );
        user.setEmail( userRegisterRequestDto.getEmail() );

        return user;
    }

    @Override
    public UserRegisterResponseDto toUserRegisterResponseDto(User user, String message) {
        if ( user == null && message == null ) {
            return null;
        }

        UserRegisterResponseDto userRegisterResponseDto = new UserRegisterResponseDto();

        if ( user != null ) {
            userRegisterResponseDto.setName( user.getName() );
            userRegisterResponseDto.setEmail( user.getEmail() );
        }
        userRegisterResponseDto.setMessage( message );

        return userRegisterResponseDto;
    }
}
