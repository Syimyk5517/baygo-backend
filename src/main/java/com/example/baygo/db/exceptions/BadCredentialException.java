package com.example.baygo.db.exceptions;

public class BadCredentialException extends RuntimeException {
    public BadCredentialException(){
    }
    public BadCredentialException(String message){
    super(message);
    }
}
