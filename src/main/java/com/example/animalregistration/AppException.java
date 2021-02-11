package com.example.animalregistration;

public class AppException extends Exception {
    public AppException(final String message) {
        super(message);
    }

    public AppException(final Throwable cause) {
        super(cause);
    }

    public AppException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
