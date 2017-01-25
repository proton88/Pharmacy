package com.suglob.pharmacy.service;

import com.suglob.pharmacy.service.exception.ServiceCheckException;
import com.suglob.pharmacy.service.exception.ServiceException;

public interface PharmacistService {
    void addQuantityDrug(int drugId, int newQuantity) throws ServiceException;

    void changePriceDrug(String drugId, String priceDrug) throws ServiceException, ServiceCheckException;

    void addDrug(String drugName, String dosage, String country, String priceDrug, String quantity, String recipe, String[] categories)
            throws ServiceException, ServiceCheckException;

    void deleteDrug(String drugId) throws ServiceException, ServiceCheckException;

    void addDrugCategory(String drugCategory) throws ServiceException, ServiceCheckException;

    void deleteDrugCategory(String drugCategory) throws ServiceException, ServiceCheckException;
}
