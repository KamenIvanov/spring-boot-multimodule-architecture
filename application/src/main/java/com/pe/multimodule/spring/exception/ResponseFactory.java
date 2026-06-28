package com.pe.multimodule.spring.exception;

import com.pe.multimodule.dto.ErrorCodeDto;
import com.pe.multimodule.dto.ResponseDto;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ResponseFactory {

    private ResponseFactory() {
        // Static helpers
    }

    public static ResponseDto create(Logger logger, Throwable e) {
        logger.error(e.getMessage());
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(e.getLocalizedMessage()));
    }

    public static ResponseDto create(Logger logger, ValidationException e, int errorCode) {
        logger.error(e.getMessage());
        return createResponse(errorCode, List.of(e.getMessage()));
    }

    public static ResponseDto create(Logger logger, int errorCode, String error) {
        logger.error("Error code: {}: Message: {}", errorCode, error);
        return createResponse(errorCode, List.of(error));
    }

    protected static ResponseDto createResponse(int statusCode, List<String> messages) {
        final var errorCode = ErrorCodeDto.resolve(statusCode);
        return new ResponseDto(errorCode, messages);
    }
}
