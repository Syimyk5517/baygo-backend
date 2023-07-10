package com.example.baygo.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionResponse {
    private HttpStatus httpStatus;
    private String ExceptionClassName;
    private String message;

    public ExceptionResponse(HttpStatus httpStatus, String exceptionClassName, String message) {
        this.httpStatus = httpStatus;
        ExceptionClassName = exceptionClassName;
        this.message = message;
    }
}
