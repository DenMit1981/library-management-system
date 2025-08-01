package com.example.library.exception;

public class InconsistentBookDataException extends RuntimeException {

    public InconsistentBookDataException(String message) {
        super(message);
    }
}
