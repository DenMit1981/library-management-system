package com.example.library.mapper;

import com.example.library.dto.request.BookRegisterRequestDto;
import com.example.library.dto.response.BookResponseDto;
import com.example.library.entity.Book;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-30T09:02:05+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public Book toEntity(BookRegisterRequestDto request) {
        if ( request == null ) {
            return null;
        }

        Book book = new Book();

        book.setIsbn( request.getIsbn() );
        book.setTitle( request.getTitle() );
        book.setAuthor( request.getAuthor() );

        return book;
    }

    @Override
    public BookResponseDto toResponseDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookResponseDto bookResponseDto = new BookResponseDto();

        bookResponseDto.setId( book.getId() );
        bookResponseDto.setIsbn( book.getIsbn() );
        bookResponseDto.setTitle( book.getTitle() );
        bookResponseDto.setAuthor( book.getAuthor() );

        return bookResponseDto;
    }

    @Override
    public List<BookResponseDto> toResponseDtoList(List<Book> books) {
        if ( books == null ) {
            return null;
        }

        List<BookResponseDto> list = new ArrayList<BookResponseDto>( books.size() );
        for ( Book book : books ) {
            list.add( toResponseDto( book ) );
        }

        return list;
    }
}
