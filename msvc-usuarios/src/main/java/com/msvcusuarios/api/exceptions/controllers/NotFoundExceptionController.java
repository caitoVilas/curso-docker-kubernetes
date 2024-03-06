package com.msvcusuarios.api.exceptions.controllers;

import com.msvcusuarios.api.exceptions.customs.NotFoundException;
import com.msvcusuarios.api.models.responses.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExceptionController {
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> notFoundHandler(NotFoundException e,
                                                            HttpServletRequest request){
        var response = ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.name())
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .path(request.getRequestURL().toString())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
