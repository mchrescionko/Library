package com.example.library.exceptions;

public class DoubleBookInBookShelfException extends IllegalArgumentException{
    String message;

    public DoubleBookInBookShelfException(String message) {
        this.message = message;
    }
}
