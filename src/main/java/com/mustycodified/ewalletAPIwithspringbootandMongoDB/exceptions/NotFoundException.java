package com.mustycodified.ewalletAPIwithspringbootandMongoDB.exceptions;

public class NotFoundException extends RuntimeException {

    private String debugMessage;
    public NotFoundException( ) {
        super();
        this.debugMessage="Resource not found";
    }

    public NotFoundException(String message){
        super(message);
        this.debugMessage=message;
    }
}
