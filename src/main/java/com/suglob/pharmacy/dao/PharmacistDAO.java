package com.suglob.pharmacy.dao;

import com.suglob.pharmacy.dao.exception.DAOException;

public interface PharmacistDAO {
    void addQuantityDrug(int drugId, int newQuantity) throws DAOException;

    boolean checkDrug(int drugIdInt) throws DAOException;

    void changePriceDrug(int drugIdInt, double priceDrugDouble) throws DAOException;

    void addDrug(String drugName, String dosage, String country, double priceDrugDouble, int quantityInt,
                 String recipe, String[] categories) throws DAOException;

    void deleteDrug(int drugIdInt) throws DAOException;

    boolean checkDrugCategory(String drugCategory) throws DAOException;

    void addDrugCategory(String drugCategory) throws DAOException;

    void deleteDrugCategory(String drugCategory) throws DAOException;

    boolean checkDrugCategoryNotEmpty(String drugCategory) throws DAOException;
}
