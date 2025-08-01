package com.example.library.mapper;

import com.example.library.dto.request.BookRegisterRequestDto;
import com.example.library.dto.response.BookResponseDto;
import com.example.library.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookRegisterRequestDto request);

    BookResponseDto toResponseDto(Book book);

    List<BookResponseDto> toResponseDtoList(List<Book> books);
}
