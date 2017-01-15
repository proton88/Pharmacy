package com.suglob.pharmacy.service;

import com.suglob.pharmacy.service.exception.ServiceCheckErrorException;
import com.suglob.pharmacy.service.exception.ServiceException;

public interface PharmacistService {
    void addQuantityDrug(int drugId, int newQuantity) throws ServiceException;

    void changePriceDrug(String drugId, String priceDrug) throws ServiceException, ServiceCheckErrorException;

    void addDrug(String drugName, String dosage, String country, String priceDrug, String quantity, String recipe, String[] categories)
            throws ServiceException, ServiceCheckErrorException;

    void deleteDrug(String drugId) throws ServiceException, ServiceCheckErrorException;

    void addDrugCategory(String drugCategory) throws ServiceException, ServiceCheckErrorException;

    void deleteDrugCategory(String drugCategory) throws ServiceException, ServiceCheckErrorException;
}
