package com.example.library.service.impl;

import com.example.library.dto.request.UserRegisterRequestDto;
import com.example.library.dto.response.UserRegisterResponseDto;
import com.example.library.entity.User;
import com.example.library.exception.UserIsPresentException;
import com.example.library.mapper.UserMapper;
import com.example.library.repository.UserRepository;
import com.example.library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String USER_IS_PRESENT = "User with login %s is already present";
    private static final String SUCCESSFUL_REGISTRATION = "User %s has been successfully registered";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserRegisterResponseDto registration(UserRegisterRequestDto userRegisterRequestDto) {
        validateUserBeforeSave(userRegisterRequestDto);

        User user = userMapper.toUser(userRegisterRequestDto);

        userRepository.save(user);

        log.info("New user : {}", user);

        return userMapper.toUserRegisterResponseDto(user, String.format(SUCCESSFUL_REGISTRATION, user.getName()));
    }

    private void validateUserBeforeSave(UserRegisterRequestDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserIsPresentException(String.format(USER_IS_PRESENT, userDto.getEmail()));
        }
    }
}
