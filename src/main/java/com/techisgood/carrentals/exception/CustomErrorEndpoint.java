package com.techisgood.carrentals.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CustomErrorEndpoint implements ErrorController {

    private final ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public ResponseEntity<ErrorDetails> handleError(WebRequest webRequest) {

        ErrorAttributeOptions options = ErrorAttributeOptions.defaults()
                .including(ErrorAttributeOptions.Include.EXCEPTION)
                .including(ErrorAttributeOptions.Include.MESSAGE)
                .including(ErrorAttributeOptions.Include.BINDING_ERRORS)
                .including(ErrorAttributeOptions.Include.STACK_TRACE);

        // Fetch all error attributes
        Map<String, Object> errorDetailsMap = errorAttributes.getErrorAttributes(webRequest, options);

        // Populate ErrorDetails object
        ErrorDetails errorDetails = new ErrorDetails();

        errorDetails.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        if (errorDetailsMap.get("status") != null) {
            errorDetails.setStatus((Integer) errorDetailsMap.get("status"));
        }
        if (errorDetailsMap.get("error") != null) {
            errorDetails.setError((String) errorDetailsMap.get("error"));
        }

        if (errorDetailsMap.get("message") != null) {
            errorDetails.setMessage((String) errorDetailsMap.get("message"));
        }

        if (errorDetailsMap.get("path") != null) {
            errorDetails.setPath((String) errorDetailsMap.get("path"));
        }

        if (errorDetailsMap.get("errors") != null) {
            errorDetails.setBindingErrors(((List<FieldError>)errorDetailsMap.get("errors")).stream().map(x -> x.toString()).toList());
        }

        if (errorDetails.getStatus() != null) {
            return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(errorDetails.getStatus()));
        }

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static String extractTopExceptionMessage(String stackTrace) {
        if (stackTrace == null || stackTrace.isEmpty()) {
            return null;
        }

        // Split the stack trace string into lines.
        String[] lines = stackTrace.split("\n");

        // The first line usually contains the exception name and its message.
        if (lines.length > 0) {
            String firstLine = lines[0];

            // Split the first line into parts using ':' and return the part after the first colon, which is usually the message.
            String[] parts = firstLine.split(":", 2);
            if (parts.length > 1) {
                return parts[1].trim();
            }
        }

        return null;
    }


}
