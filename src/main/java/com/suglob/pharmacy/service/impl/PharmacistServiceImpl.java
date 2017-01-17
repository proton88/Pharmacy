package com.suglob.pharmacy.service.impl;

import com.suglob.pharmacy.dao.DAOFactory;
import com.suglob.pharmacy.dao.PharmacistDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.service.PharmacistService;
import com.suglob.pharmacy.service.exception.ServiceCheckErrorException;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.utils.ConstantClass;
import com.suglob.pharmacy.utils.Validator;

public class PharmacistServiceImpl implements PharmacistService {
    @Override
    public void addQuantityDrug(int drugId, int newQuantity) throws ServiceException {
        PharmacistDAO pharmacistDAO = DAOFactory.getInstance().getPharmacistDAO();
        try {
            pharmacistDAO.addQuantityDrug(drugId, newQuantity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void changePriceDrug(String drugId, String priceDrug) throws ServiceException, ServiceCheckErrorException {
        if (!Validator.checkInteger(drugId) || !Validator.checkDouble(priceDrug)){
            throw new ServiceCheckErrorException(ConstantClass.WRONG_FORMAT);
        }
        int drugIdInt=Integer.parseInt(drugId);
        double priceDrugDouble=Double.parseDouble(priceDrug);
        Validator.checkDrugExist(drugIdInt);

        PharmacistDAO pharmacistDAO = DAOFactory.getInstance().getPharmacistDAO();
        try {
            pharmacistDAO.changePriceDrug(drugIdInt, priceDrugDouble);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void addDrug(String drugName, String dosage, String country, String priceDrug, String quantity, String recipe, String[] categories) throws ServiceException, ServiceCheckErrorException {
        Validator.checkAddDrug(drugName, dosage, country, priceDrug, quantity, recipe, categories);
        if (!Validator.checkInteger(quantity) || !Validator.checkDouble(priceDrug)){
            throw new ServiceCheckErrorException(ConstantClass.WRONG_FORMAT);
        }
        int quantityInt=Integer.parseInt(quantity);
        double priceDrugDouble=Double.parseDouble(priceDrug);

        drugName=drugName.toLowerCase();
        drugName=drugName.substring(0, 1).toUpperCase() + drugName.substring(1);

        country=country.toLowerCase();
        country=country.substring(0, 1).toUpperCase() + country.substring(1);

        PharmacistDAO pharmacistDAO = DAOFactory.getInstance().getPharmacistDAO();
        try {
            pharmacistDAO.addDrug(drugName, dosage, country, priceDrugDouble, quantityInt, recipe, categories);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteDrug(String drugId) throws ServiceException, ServiceCheckErrorException {
        if (!Validator.checkInteger(drugId)){
            throw new ServiceCheckErrorException(ConstantClass.WRONG_FORMAT);
        }
        int drugIdInt=Integer.parseInt(drugId);
        Validator.checkDrugExist(drugIdInt);

        PharmacistDAO pharmacistDAO = DAOFactory.getInstance().getPharmacistDAO();
        try {
            pharmacistDAO.deleteDrug(drugIdInt);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addDrugCategory(String drugCategory) throws ServiceException, ServiceCheckErrorException {
        Validator.checkDrugCategoryExist(drugCategory);

        PharmacistDAO pharmacistDAO = DAOFactory.getInstance().getPharmacistDAO();
        try {
            pharmacistDAO.addDrugCategory(drugCategory);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteDrugCategory(String drugCategory) throws ServiceException, ServiceCheckErrorException {
        Validator.checkDrugCategoryNotExist(drugCategory);
        Validator.checkDrugCategoryNotEmpty(drugCategory);

        PharmacistDAO pharmacistDAO = DAOFactory.getInstance().getPharmacistDAO();
        try {
            pharmacistDAO.deleteDrugCategory(drugCategory);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
