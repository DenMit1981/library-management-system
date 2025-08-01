package com.example.library.controller;

import com.example.library.dto.request.BookRegisterRequestDto;
import com.example.library.dto.response.BookResponseDto;
import com.example.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerBook_ReturnsCreatedBook() throws Exception {
        BookRegisterRequestDto requestDto = new BookRegisterRequestDto();
        requestDto.setIsbn("1234567890");
        requestDto.setTitle("Test Book");
        requestDto.setAuthor("Author Name");

        BookResponseDto responseDto = new BookResponseDto();
        responseDto.setIsbn("1234567890");
        responseDto.setTitle("Test Book");
        responseDto.setAuthor("Author Name");

        Mockito.when(bookService.registerBook(any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/books/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value("1234567890"))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Author Name"));
    }

    @Test
    void getBooks_ReturnsListOfBooks() throws Exception {
        BookResponseDto dto = new BookResponseDto();
        dto.setIsbn("1234567890");
        dto.setTitle("Book 1");
        dto.setAuthor("Author 1");

        List<BookResponseDto> books = Collections.singletonList(dto);

        Mockito.when(bookService.getAllBooks(anyInt(), anyInt(), anyString(), anyString())).thenReturn(books);

        mockMvc.perform(get("/api/v1/books?page=0&size=10&sortBy=title&sortDir=asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Book 1"));
    }

    @Test
    void borrowBook_ReturnsUpdatedBook() throws Exception {
        BookResponseDto dto = new BookResponseDto();
        dto.setIsbn("9876543210");
        dto.setTitle("Borrowed Book");
        dto.setAuthor("User A");

        Mockito.when(bookService.borrowBook(eq(1L), eq(2L))).thenReturn(dto);

        mockMvc.perform(post("/api/v1/books/2/borrow")
                        .param("borrowerId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Borrowed Book"));
    }

    @Test
    void returnBook_ReturnsUpdatedBook() throws Exception {
        BookResponseDto dto = new BookResponseDto();
        dto.setIsbn("1111222233");
        dto.setTitle("Returned Book");
        dto.setAuthor("User B");

        Mockito.when(bookService.returnBook(eq(1L), eq(3L))).thenReturn(dto);

        mockMvc.perform(post("/api/v1/books/3/return")
                        .param("borrowerId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Returned Book"));
    }
}