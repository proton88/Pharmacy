package com.suglob.pharmacy.service;

import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.service.exception.ServiceRegistrationException;

public interface ClientService {
    User registration(String login, String password, String passwordRepeat, String name, String surname,String patronymic,
                      String adress, String passportId) throws ServiceException, ServiceRegistrationException;
}
