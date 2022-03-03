package com.example.library.service.exceptions;

import java.util.function.Supplier;

public class MyResourceNotFoundException extends RuntimeException  {
    public MyResourceNotFoundException() {
        super();
    }
    public MyResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public MyResourceNotFoundException(String message) {
        super(message);
    }
    public MyResourceNotFoundException(Throwable cause) {
        super(cause);
    }


}