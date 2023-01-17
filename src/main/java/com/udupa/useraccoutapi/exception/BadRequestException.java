package com.udupa.useraccoutapi.exception;

public class BadRequestException extends Exception {

    public BadRequestException(String errorMsg) {
        super(errorMsg);
    }
}
