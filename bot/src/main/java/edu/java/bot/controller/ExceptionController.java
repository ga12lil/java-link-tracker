package edu.java.bot.controller;

import edu.java.bot.dto.ApiErrorResponse;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = {
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMediaTypeNotSupportedException.class,
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse badRequest(Exception ex) {
        return toApiError(ex, "Incorrect response", HttpStatus.BAD_REQUEST.toString());
    }

    private ApiErrorResponse toApiError(Throwable ex, String description, String code) {
        String exName = ex.getClass().getName();
        String exMsg = ex.getMessage();
        List<String> stacktrace = getStacktrace(ex);
        return new ApiErrorResponse(description, code, exName, exMsg, stacktrace);
    }

    private List<String> getStacktrace(Throwable ex) {
        return Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList();
    }
}
