package com.example.library.service;

import com.example.library.dto.request.BookRegisterRequestDto;
import com.example.library.dto.response.BookResponseDto;

import java.util.List;

public interface BookService {

    BookResponseDto registerBook(BookRegisterRequestDto bookRegisterRequestDto);

    List<BookResponseDto> getAllBooks(int page, int size, String sortBy, String sortDir);

    BookResponseDto borrowBook(Long borrowerId, Long bookId);

    BookResponseDto returnBook(Long borrowerId, Long bookId);
}
