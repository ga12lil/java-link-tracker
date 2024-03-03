package edu.java.controller;

import edu.java.dto.ApiErrorResponse;
import edu.java.exception.ChatNotFoundException;
import edu.java.exception.LinkNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = {LinkNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse linkNotFound(LinkNotFoundException ex) {
        return new ApiErrorResponse("Link not found",
                HttpStatus.NOT_FOUND.toString(),
                ex.getClass().getName(),
                ex.getMessage(),
                Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList());
    }

    @ExceptionHandler(value = {ChatNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse linkNotFound(ChatNotFoundException ex) {
        return new ApiErrorResponse("Chat not found",
                HttpStatus.NOT_FOUND.toString(),
                ex.getClass().getName(),
                ex.getMessage(),
                Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList());
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse serverError(Exception ex) {
        return new ApiErrorResponse("Некорректные параметры запроса",
                HttpStatus.BAD_REQUEST.toString(),
                ex.getClass().getName(),
                ex.getMessage(),
                Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList());
    }

    //TODO: add handlers for LinkAlreadyAddedException and ChatAlreadyRegisteredException
}
