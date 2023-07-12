package com.example.baygo.db.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(){
    }
    public NotFoundException(String message){
    super(message);
    }
}
