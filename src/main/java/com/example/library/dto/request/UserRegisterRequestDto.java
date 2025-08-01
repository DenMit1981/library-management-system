package com.example.library.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class UserRegisterRequestDto {

    private static final String NAME = "Enter name";
    private static final String EMAIL = "Enter email";
    private static final String WRONG_SIZE_OF_NAME = "Name shouldn't be less than 2 symbols";
    private static final String INVALID_NUMBER_OF_SYMBOLS_FOR_EMAIL = "Email shouldn't be less than 6 symbols";
    private static final String INVALID_EMAIL = "Email should contain symbol @";

    @NotBlank(message = NAME)
    @Size(min = 2, message = WRONG_SIZE_OF_NAME)
    private String name;

    @NotBlank(message = EMAIL)
    @Size(min = 6, message = INVALID_NUMBER_OF_SYMBOLS_FOR_EMAIL)
    @Email(regexp = "^[^@|\\.].+@.+\\..+[^@|\\.]$", message = INVALID_EMAIL)
    private String email;
}
