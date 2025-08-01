package com.example.library.service;

import com.example.library.dto.request.BookRegisterRequestDto;
import com.example.library.dto.response.BookResponseDto;
import com.example.library.entity.Book;
import com.example.library.entity.User;
import com.example.library.exception.BookIsTakenException;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private User user;
    private BookRegisterRequestDto registerDto;
    private BookResponseDto responseDto;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Title");
        book.setAuthor("Author");
        book.setIsbn("12345");

        user = new User();
        user.setId(1L);
        user.setName("John");

        registerDto = new BookRegisterRequestDto();
        registerDto.setTitle("Title");
        registerDto.setAuthor("Author");
        registerDto.setIsbn("12345");

        responseDto = new BookResponseDto();
        responseDto.setTitle("Title");
        responseDto.setAuthor("Author");
        responseDto.setIsbn("12345");
    }

    @Test
    void registerBook_shouldRegisterBook_whenValidRequest() {
        when(bookRepository.findAllByIsbn("12345")).thenReturn(Collections.emptyList());
        when(bookMapper.toEntity(registerDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toResponseDto(book)).thenReturn(responseDto);

        BookResponseDto result = bookService.registerBook(registerDto);

        assertEquals("Title", result.getTitle());
        verify(bookRepository).save(book);
    }

    @Test
    void borrowBook_shouldSetBorrower_whenBookIsFree() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookMapper.toResponseDto(book)).thenReturn(responseDto);

        BookResponseDto result = bookService.borrowBook(1L, 1L);

        assertEquals("Title", result.getTitle());
        assertEquals(user, book.getBorrower());
        verify(bookRepository).save(book);
    }

    @Test
    void borrowBook_shouldThrowException_whenBookIsAlreadyTaken() {
        book.setBorrower(user);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThrows(BookIsTakenException.class, () -> bookService.borrowBook(1L, 1L));
    }

    @Test
    void returnBook_shouldRemoveBorrower_whenCorrectUserReturns() {
        book.setBorrower(user);
        user.getBorrowedBooks().add(book);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toResponseDto(book)).thenReturn(responseDto);

        BookResponseDto result = bookService.returnBook(1L, 1L);

        assertNull(book.getBorrower());
        verify(bookRepository).save(book);
    }

    @Test
    void returnBook_shouldThrowException_whenBookDoesNotBelongToUser() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        book.setBorrower(anotherUser);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThrows(BookIsTakenException.class, () -> bookService.returnBook(1L, 1L));
    }

    @Test
    void getAllBooks_shouldReturnSortedAndPaginatedBooks() {
        List<Book> books = Collections.singletonList(book);
        Page<Book> bookPage = new PageImpl<>(books);

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        when(bookMapper.toResponseDtoList(books)).thenReturn(Collections.singletonList(responseDto));

        List<BookResponseDto> result = bookService.getAllBooks(0, 10, "title", "asc");

        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle());
    }
}

