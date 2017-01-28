package com.suglob.pharmacy.dao;

import com.suglob.pharmacy.dao.exception.DAOException;

import java.util.List;
import java.util.Map;

public interface DoctorDAO {
    Map<String,List> checkRecipe(int userId) throws DAOException;

    void cancelRecipe(int userId, String drugName, int clientId) throws DAOException;

    boolean checkDrugAndClient(int drugId, int clientId) throws DAOException;

    String assignRecipe(int userId, int drugId, int quantity, int period, int clientId, String code) throws DAOException;

    boolean checkDrugCode(String code) throws DAOException;

    void cancelExtendRecipe(String codeRecipe) throws DAOException;

    void extendRecipe(int period, String codeRecipe) throws DAOException;
}
