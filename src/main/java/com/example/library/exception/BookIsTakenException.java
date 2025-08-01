package com.example.library.exception;

public class BookIsTakenException extends RuntimeException {

    public BookIsTakenException(String msg) {
        super(msg);
    }
}
