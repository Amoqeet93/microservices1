package com.example.Loans.exception;

public class LoansAlreadyExistsException extends RuntimeException {
    public LoansAlreadyExistsException(String message) {
        super(message);
    }
}
