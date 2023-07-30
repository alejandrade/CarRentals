package com.techisgood.carrentals.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomErrorEndpoint implements ErrorController {

    private final ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public ResponseEntity<ErrorDetails> handleError(WebRequest webRequest) {
        try {
            ErrorAttributeOptions options = ErrorAttributeOptions.defaults()
                    .including(ErrorAttributeOptions.Include.EXCEPTION)
                    .including(ErrorAttributeOptions.Include.MESSAGE)
                    .including(ErrorAttributeOptions.Include.BINDING_ERRORS)
                    .including(ErrorAttributeOptions.Include.STACK_TRACE);

            // Fetch all error attributes
            Map<String, Object> errorDetailsMap = errorAttributes.getErrorAttributes(webRequest, options);

            // Populate ErrorDetails object
            ErrorDetails errorDetails = new ErrorDetails();

            errorDetails.setTimestamp(convertToString(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
            errorDetails.setStatus(convertToInteger(errorDetailsMap.getOrDefault("status", HttpStatus.INTERNAL_SERVER_ERROR.value())));
            errorDetails.setError(convertToString(errorDetailsMap.getOrDefault("error", "Unknown Error")));
            errorDetails.setMessage(convertToString(errorDetailsMap.getOrDefault("message", "No message available")));
            errorDetails.setPath(convertToString(errorDetailsMap.get("path")));

            // Check if the "errors" attribute exists and is a list of objects
            if (errorDetailsMap.containsKey("errors") && errorDetailsMap.get("errors") instanceof List<?>) {
                List<?> errorsList = (List<?>) errorDetailsMap.get("errors");
                List<String> bindingErrors = errorsList.stream()
                        .map((obj) -> {
                            if(obj instanceof MessageSourceResolvable) {
                                var msr = (MessageSourceResolvable) obj;
                                return msr.getDefaultMessage();
                            } else {
                                log.warn("error not properly being parsed in CustomErrorEndpoint");
                                return obj.toString();
                            }
                        })
                        .collect(Collectors.toList());
                errorDetails.setBindingErrors(bindingErrors);
            }

            return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(errorDetails.getStatus()));
        } catch (Exception ex) {
            // In case of any unexpected exception, create an ErrorDetails object representing a 500 Internal Server Error
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorDetails.setError("Internal Server Error");
            errorDetails.setMessage("An unexpected error occurred while processing the request." + ex.getMessage());
            return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private String convertToString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else {
            return obj.toString();
        }
    }

    private Integer convertToInteger(Object obj) {
        try {
            return Integer.parseInt(obj.toString());
        } catch (NumberFormatException | NullPointerException ex) {
            return null;
        }
    }


}
