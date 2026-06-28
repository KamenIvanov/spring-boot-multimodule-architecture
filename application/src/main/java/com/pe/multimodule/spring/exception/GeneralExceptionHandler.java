package com.pe.multimodule.spring.exception;

import com.pe.multimodule.domain.exceptions.*;
import com.pe.multimodule.dto.ResponseDto;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    protected static final Logger logger = LoggerFactory.getLogger(GeneralExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final var err = ResponseFactory.create(logger, status.value(), ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public final ResponseEntity<ResponseDto> handleException(IllegalArgumentException e) {
        return handleException(new ValidationException(e.getMessage()));
    }

    @ExceptionHandler({ ValidationException.class })
    public final ResponseEntity<ResponseDto> handleException(ValidationException e) {
        final var err = ResponseFactory.create(logger, e, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler({ NotFoundException.class })
    public final ResponseEntity<ResponseDto> handleException(NotFoundException e) {
        final var err = ResponseFactory.create(logger, HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler({ NoSuchElementException.class })
    public final ResponseEntity<ResponseDto> handleException(NoSuchElementException e) {
        final var err = ResponseFactory.create(logger, HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler({ ConflictException.class })
    public final ResponseEntity<ResponseDto> handleException(ConflictException e) {
        final var err = ResponseFactory.create(logger, HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler({ ConcurrentModificationException.class })
    public final ResponseEntity<ResponseDto> handleException(ConcurrentModificationException e) {
        final var err = ResponseFactory.create(logger, HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(err);
    }

    @ExceptionHandler({ AuthorizationException.class })
    public final ResponseEntity<ResponseDto> handleException(AuthorizationException e) {
        final var err = ResponseFactory.create(logger, HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler({ ForbiddenException.class })
    public final ResponseEntity<ResponseDto> handleException(ForbiddenException e) {
        final var err = ResponseFactory.create(logger, HttpStatus.FORBIDDEN.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler({ VerificationException.class })
    public final ResponseEntity<ResponseDto> handleException(VerificationException e) {
        final var err = ResponseFactory.create(logger, HttpStatus.FORBIDDEN.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler({ InvalidSessionException.class })
    public final ResponseEntity<ResponseDto> handleException(InvalidSessionException e) {
        final var error = ResponseFactory.create(logger, HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler({ Throwable.class })
    public final ResponseEntity<ResponseDto> handleException(Throwable e) {
        final var err = ResponseFactory.create(logger, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}