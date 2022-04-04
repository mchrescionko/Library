package com.example.library.exceptions;

public class MyException extends RuntimeException{
    String message;

    public MyException(String message) {
        this.message = message;
    }
}
