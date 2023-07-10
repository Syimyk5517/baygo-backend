package com.example.baygo.exceptions.handler;

import com.example.baygo.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse alreadyExistException(AlreadyExistException a){
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                a.getMessage(),
                a.getClass().getSimpleName());
    }
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse badCredentialException(BadCredentialException b){
        return new ExceptionResponse(
                HttpStatus.FORBIDDEN,
                b.getMessage(),
                b.getClass().getSimpleName());
    }
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse badRequestException(BadRequestException badRequestException){
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                badRequestException.getMessage(),
                badRequestException.getClass().getSimpleName());
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundException(NotFoundException n){
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                n.getMessage(),
                n.getClass().getSimpleName());
    }
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse authenticationException(AuthenticationException a){
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                a.getMessage(),
                a.getClass().getSimpleName());
    }
    @ExceptionHandler(MessageSendingException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse messageSendingException(MessageSendingException m){
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                m.getMessage(),
                m.getClass().getSimpleName());
    }




}
