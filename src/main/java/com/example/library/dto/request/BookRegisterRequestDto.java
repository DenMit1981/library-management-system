package com.example.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class BookRegisterRequestDto {

    private static final String ISBN = "Enter ISBN";
    private static final String TITLE = "Enter title";
    private static final String AUTHOR = "Enter author";

    @NotBlank(message = ISBN)
    private String isbn;

    @NotBlank(message = TITLE)
    private String title;

    @NotBlank(message = AUTHOR)
    private String author;
}
