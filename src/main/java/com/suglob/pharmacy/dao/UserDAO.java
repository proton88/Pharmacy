package com.suglob.pharmacy.dao;

import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.entity.User;

public interface UserDAO {
    User registration(String login, String password, String passwordRepeat, String name, String surname, String patronymic,
                      String adress, String passportId) throws DAOException;
}
