package com.techisgood.carrentals.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetails handleNoSuchElementException(NoSuchElementException ex) {
        log.error("Collection Empty", ex);
        return ErrorDetails.builder()
                .status(404)
                .error(ex.getMessage())
                .message("not found")
                .build();
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleSqlConstraintException(NoSuchElementException ex) {
        log.error("Collection Empty", ex);
        return ErrorDetails.builder()
                .status(400)
                .error(ex.getMessage())
                .message("duplicate")
                .build();
    }

}
