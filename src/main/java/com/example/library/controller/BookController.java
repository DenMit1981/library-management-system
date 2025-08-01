package com.example.library.controller;

import com.example.library.dto.request.BookRegisterRequestDto;
import com.example.library.dto.response.BookResponseDto;
import com.example.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
@Tag(name = "Book controller")
public class BookController {

    private final BookService bookService;

    @PostMapping("/register")
    @Operation(summary = " Register a new book to the library")
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDto registerBook(@RequestBody @Valid BookRegisterRequestDto bookRegisterRequestDto) {
        return bookService.registerBook(bookRegisterRequestDto);
    }

    @GetMapping
    @Operation(summary = "Get a list of all books in the library with sort sort and pagination")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponseDto> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return bookService.getAllBooks(page, size, sortBy, sortDir);
    }

    @PostMapping("/{bookId}/borrow")
    @Operation(summary = "Borrow a book for a user")
    @ResponseStatus(HttpStatus.OK)
    public BookResponseDto borrowBook(
            @PathVariable Long bookId,
            @RequestParam Long borrowerId) {
        return bookService.borrowBook(borrowerId, bookId);
    }

    @PostMapping("/{bookId}/return")
    @Operation(summary = "Return a borrowed book")
    @ResponseStatus(HttpStatus.OK)
    public BookResponseDto returnBook(
            @PathVariable Long bookId,
            @RequestParam Long borrowerId) {
        return bookService.returnBook(borrowerId, bookId);
    }
}
