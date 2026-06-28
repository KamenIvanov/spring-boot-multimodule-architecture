package com.pe.multimodule.domain.exceptions;

public class InvalidSessionException extends RuntimeException {

    public InvalidSessionException(String message) {
        super(message);
    }

    public InvalidSessionException(String message, Throwable cause) {
        super(message, cause);
    }

}
