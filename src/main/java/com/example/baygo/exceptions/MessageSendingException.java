package com.example.baygo.exceptions;

public class MessageSendingException extends RuntimeException{
    public MessageSendingException (){
    }
    public MessageSendingException ( String message){
        super(message);
    }
}
