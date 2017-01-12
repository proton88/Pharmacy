package com.suglob.pharmacy.service;

import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.service.exception.ServiceCheckErrorException;

import java.util.List;

public interface ClientService {
    User registration(String login, String password, String passwordRepeat, String name, String surname,String patronymic,
                      String adress, String passportId) throws ServiceException, ServiceCheckErrorException;

    String payOrder(List<Drug> orderList) throws ServiceException;

    int addRecipe(String recipeCode, int count, int id) throws ServiceException;

    void cancelOrder(int count, int id, int id_recipe) throws ServiceException;

    String orderRecipe(String drugName, String doctorSurname, int userId) throws ServiceException;

    String orderExtendRecipe(String codeDrug) throws ServiceException;
}
