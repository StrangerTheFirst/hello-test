package com.assignments.exception;

public class UnprocessableEntityException extends Exception {
    public UnprocessableEntityException() {
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }
}
