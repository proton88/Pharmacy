package com.suglob.pharmacy.service.exception;

public class ServiceCheckErrorException extends Exception {
    public ServiceCheckErrorException(){}

    public ServiceCheckErrorException(String message){
        super(message);
    }

    public ServiceCheckErrorException(String message, Exception e){
        super(message,e);
    }

    public ServiceCheckErrorException(Exception e){
        super(e);
    }
}
