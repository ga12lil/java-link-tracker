package edu.java.bot.controller;

import edu.java.bot.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.Arrays;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = {
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMediaTypeNotSupportedException.class,
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse badRequest(Exception ex) {
        return new ApiErrorResponse(
                "Incorrect response",
                HttpStatus.BAD_REQUEST.toString(),
                ex.getClass().getName(),
                ex.getMessage(),
                Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList());
    }
}
