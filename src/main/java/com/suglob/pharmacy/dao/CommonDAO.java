package com.suglob.pharmacy.dao;

import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.entity.DrugCategory;
import com.suglob.pharmacy.entity.User;

import java.util.ArrayList;

public interface CommonDAO {
    User logination(String login, String password) throws DAOException;
    ArrayList<DrugCategory> takeDrugCategories() throws DAOException;

    ArrayList<Drug> takeDrugs(String str) throws DAOException;
}
