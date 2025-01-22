package com.mindhub.user_microservice.exceptions;

public class GenericException extends RuntimeException {
    public GenericException(String message) {
        super(message);
    }
}
