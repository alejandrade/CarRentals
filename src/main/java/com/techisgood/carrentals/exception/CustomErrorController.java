package com.techisgood.carrentals.exception;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public ResponseEntity<ErrorDetails> handleError(WebRequest webRequest) {

        ErrorAttributeOptions options = ErrorAttributeOptions.defaults()
                .including(ErrorAttributeOptions.Include.STACK_TRACE)
                .including(ErrorAttributeOptions.Include.EXCEPTION)
                .including(ErrorAttributeOptions.Include.BINDING_ERRORS);
        // Fetch all error attributes
        Map<String, Object> errorDetailsMap = errorAttributes.getErrorAttributes(webRequest, options);

        // Populate ErrorDetails object
        ErrorDetails errorDetails = new ErrorDetails();

        errorDetails.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        errorDetails.setStatus((Integer) errorDetailsMap.get("status"));
        errorDetails.setError((String) errorDetailsMap.get("error"));
        errorDetails.setMessage((String) errorDetailsMap.get("message"));
        errorDetails.setPath((String) errorDetailsMap.get("path"));

        if (errorDetails.getStatus() != null) {
            return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(errorDetails.getStatus()));
        }

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
