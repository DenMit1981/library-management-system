package com.example.library.service.impl;

import com.example.library.dto.request.BookRegisterRequestDto;
import com.example.library.dto.response.BookResponseDto;
import com.example.library.entity.Book;
import com.example.library.entity.User;
import com.example.library.exception.BookIsTakenException;
import com.example.library.exception.BookNotFoundException;
import com.example.library.exception.InconsistentBookDataException;
import com.example.library.exception.UserNotFoundException;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private static final String USER_NOT_FOUND = "User with %s %s not found";
    private static final String BOOK_NOT_FOUND = "Book with %s %s not found";
    private static final String BOOK_IS_ALREADY_TAKEN = "The book is already taken by another user";
    private static final String BOOK_DOES_NOT_BELONG = "This book does not belong to the user with %s %s";

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public BookResponseDto registerBook(BookRegisterRequestDto bookRegisterRequestDto) {
        List<Book> existingBooks = bookRepository.findAllByIsbn(bookRegisterRequestDto.getIsbn());

        for (Book existingBook : existingBooks) {
            validateBookDetails(existingBook, bookRegisterRequestDto);
        }

        Book saved = bookRepository.save(bookMapper.toEntity(bookRegisterRequestDto));

        log.info("New book has been registered successfully");

        return bookMapper.toResponseDto(saved);
    }

    @Override
    public List<BookResponseDto> getAllBooks(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        List<Book> books = bookRepository.findAll(pageable).getContent();

        return bookMapper.toResponseDtoList(books);
    }

    @Override
    @Transactional
    public BookResponseDto borrowBook(Long borrowerId, Long bookId) {
        Book book = getBookById(bookId);

        if (book.getBorrower() != null) {
            throw new BookIsTakenException(BOOK_IS_ALREADY_TAKEN);
        }

        User borrower = getUserById(borrowerId);

        borrower.addBook(book);
        bookRepository.save(book);

        log.info("Book with id {} has been borrowed by user with id {}", bookId, borrowerId);

        return bookMapper.toResponseDto(book);
    }

    @Override
    @Transactional
    public BookResponseDto returnBook(Long borrowerId, Long bookId) {
        Book book = getBookById(bookId);

        if (book.getBorrower() == null || !book.getBorrower().getId().equals(borrowerId)) {
            throw new BookIsTakenException(String.format(BOOK_DOES_NOT_BELONG, "id", borrowerId));
        }

        User borrower = book.getBorrower();
        borrower.removeBook(book);
        bookRepository.save(book);

        log.info("Book with id {} has been returned by user with id {}", bookId, borrowerId);

        return bookMapper.toResponseDto(book);
    }

    private Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(String.format(BOOK_NOT_FOUND, "id", bookId)));
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, "id", userId)));
    }

    private void validateBookDetails(Book book, BookRegisterRequestDto dto) {
        if (!book.getTitle().equals(dto.getTitle()) || !book.getAuthor().equals(dto.getAuthor())) {
            throw new InconsistentBookDataException("Book with same ISBN must have same title and author");
        }
    }
}
