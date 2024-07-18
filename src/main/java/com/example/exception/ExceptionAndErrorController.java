package com.example.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.auth.message.AuthException;


@RestControllerAdvice
public class ExceptionAndErrorController {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        return ex.getParameterName() + " is missing in the query parameters and is required";
    }

    @ExceptionHandler(badFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadFormat(badFormatException ex) { return  ex.getMessage(); }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleResourceNotFound(ResourceNotFoundException ex) { return ex.getMessage(); }

    @ExceptionHandler(duplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateUsername(duplicateUsernameException ex) { return ex.getMessage(); }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAuthenticationError(AuthException ex) { return ex.getMessage(); }
}
