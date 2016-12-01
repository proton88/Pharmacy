package com.suglob.pharmacy.service.exception;

public class ServiceRegistrationException extends Exception {
    public ServiceRegistrationException(){}

    public ServiceRegistrationException(String message){
        super(message);
    }

    public ServiceRegistrationException(String message, Exception e){
        super(message,e);
    }

    public ServiceRegistrationException(Exception e){
        super(e);
    }
}
