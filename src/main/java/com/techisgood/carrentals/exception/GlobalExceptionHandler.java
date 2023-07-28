package com.techisgood.carrentals.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ServerErrorException;

import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorAttributes errorAttributes;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        // Customize your response here
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Custom Access Denied Message");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        // Customize your response here
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Custom Authentication Failed Message");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorDetails> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest webRequest) {
        Map<String, Object> errorMap = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(errorMap.get("timestamp").toString());
        errorDetails.setStatus((int) errorMap.get("status"));
        errorDetails.setError(errorMap.get("error").toString());
        errorDetails.setMessage(composeValidationErrorMessage(ex)); // Create a method to compose a message
        errorDetails.setPath(errorMap.get("path").toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    private String composeValidationErrorMessage(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder("Validation failed for: ");
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            sb.append(fieldName).append(": ").append(errorMessage).append(". ");
        });
        return sb.toString().trim();
    }


    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Invalid Input: " + ex.getMessage(), 400);
        return ResponseEntity.badRequest().body(error);
    }


    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Not Found: " + ex.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(value = {RemoteServiceException.class})
    protected ResponseEntity<ErrorResponse> handleServerError(RemoteServiceException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Remote Service Error: " + ex.service.getName() + ": " + ex.getMessage(), 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


    @ExceptionHandler(value = {ServerErrorException.class})
    protected ResponseEntity<ErrorResponse> handleServerError(ServerErrorException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Server Error: " + ex.getMessage(), 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


}
