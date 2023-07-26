package com.techisgood.carrentals.exception;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ServerErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Invalid Input: " + ex.getMessage(), 400);
        return ResponseEntity.badRequest().body(error);
    }

    
    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Not Found: " + ex.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    
    @ExceptionHandler(value = { RemoteServiceException.class })
    protected ResponseEntity<ErrorResponse> handleServerError(RemoteServiceException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Remote Service Error: " + ex.service.getName() + ": " + ex.getMessage(), 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    
    
    @ExceptionHandler(value = { ServerErrorException.class })
    protected ResponseEntity<ErrorResponse> handleServerError(ServerErrorException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Server Error: " + ex.getMessage(), 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    
    
    
}
