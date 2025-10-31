package com.tung.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistedException extends RuntimeException {
    public CustomerAlreadyExistedException(String message) {
        super(message);
    }
}
