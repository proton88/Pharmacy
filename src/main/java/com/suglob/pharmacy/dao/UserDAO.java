package com.suglob.pharmacy.dao;

import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.entity.User;

import java.util.List;

public interface UserDAO {
    User registration(String login, String password, String passwordRepeat, String name, String surname, String patronymic,
                      String adress, String passportId, String email) throws DAOException;

    String payOrder(List<Drug> orderList) throws DAOException;

    int addRecipe(String recipeCode, int count, int id) throws DAOException;

    void cancelOrder(int count, int id, int id_recipe) throws DAOException;

    String drugExists(String drugName) throws DAOException;

    void orderRecipe(String drugName, String doctorSurname, int userId) throws DAOException;

    String recipeExists(String codeDrug) throws DAOException;

    void orderExtendRecipe(String codeDrug) throws DAOException;
}
