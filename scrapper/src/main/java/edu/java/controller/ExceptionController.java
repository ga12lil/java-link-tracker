package edu.java.controller;

import edu.java.dto.ApiErrorResponse;
import edu.java.exception.ChatNotFoundException;
import edu.java.exception.LinkNotFoundException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = {LinkNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse linkNotFound(LinkNotFoundException ex) {
        return toApiError(ex, "Link not found", HttpStatus.NOT_FOUND.toString());
    }

    @ExceptionHandler(value = {ChatNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse chatNotFound(ChatNotFoundException ex) {
        return toApiError(ex, "Chat not found", HttpStatus.NOT_FOUND.toString());
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse serverError(Exception ex) {
        return toApiError(ex, "Некорректные параметры запроса", HttpStatus.BAD_REQUEST.toString());
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

    //TODO: add handlers for LinkAlreadyAddedException and ChatAlreadyRegisteredException
}
