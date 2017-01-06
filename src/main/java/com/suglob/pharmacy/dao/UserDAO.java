package com.suglob.pharmacy.dao;

import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.entity.User;

import java.util.List;

public interface UserDAO {
    User registration(String login, String password, String passwordRepeat, String name, String surname, String patronymic,
                      String adress, String passportId) throws DAOException;

    String payOrder(List<Drug> orderList) throws DAOException;
}
