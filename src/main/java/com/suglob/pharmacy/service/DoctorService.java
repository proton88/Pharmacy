package com.suglob.pharmacy.service;

import com.suglob.pharmacy.service.exception.ServiceCheckErrorException;
import com.suglob.pharmacy.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface DoctorService {
    Map<String,List> checkRecipe(int user_id) throws ServiceException;

    void cancelRecipe(int userId, String drugName, int clientId) throws ServiceException;

    String assignRecipe(int userId, int drugId, int quantity, int period, int clientId, String code) throws ServiceException,
            ServiceCheckErrorException;

    void cancelExtendRecipe(String codeRecipe) throws ServiceException;

    void extendRecipe(int period, String codeRecipe) throws ServiceException, ServiceCheckErrorException;
}
