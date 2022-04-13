package com.example.library.exceptions;

public class NoSuchBookException extends NoSuchFieldException{
    String message;

    public NoSuchBookException(String message) {
        this.message = message;
    }
}
