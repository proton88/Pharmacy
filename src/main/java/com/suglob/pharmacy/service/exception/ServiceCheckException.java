package com.suglob.pharmacy.service.exception;

public class ServiceCheckException extends Exception {
    public ServiceCheckException(){}

    public ServiceCheckException(String message){
        super(message);
    }

    public ServiceCheckException(String message, Exception e){
        super(message,e);
    }

    public ServiceCheckException(Exception e){
        super(e);
    }
}
