package com.example.library.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponseDto {

    private Long id;

    private String isbn;

    private String title;

    private String author;
}
