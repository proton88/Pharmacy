package com.suglob.pharmacy.service.impl;

import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.dao.DAOFactory;
import com.suglob.pharmacy.dao.PharmacistDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.service.PharmacistService;
import com.suglob.pharmacy.service.exception.ServiceCheckException;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.validation.Validator;
/**
 * This class contains methods to verify the data and transmit them to the DAO layer.
 * All methods are associated with the user: 'pharmacist'.
 */
public class PharmacistServiceImpl implements PharmacistService {
    /**
     * Method add count drugs
     *
     * @param drugId drug id which must be added the amount
     * @param newQuantity the quantity to be set
     * @throws ServiceException if DaoException is thrown
     */
    @Override
    public void addQuantityDrug(int drugId, int newQuantity) throws ServiceException {
        PharmacistDAO pharmacistDAO = DAOFactory.getInstance().getPharmacistDAO();
        try {
            pharmacistDAO.addQuantityDrug(drugId, newQuantity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }
    /**
     * Method validate data and change price for drug
     *
     * @param drugId drug id which must be changed the price
     * @param priceDrug the price to be set
     * @throws ServiceException if DaoException is thrown
     * @throws ServiceCheckException if wrong parameters or drug not exist.
     */
    @Override
    public void changePriceDrug(String drugId, String priceDrug) throws ServiceException, ServiceCheckException {
        if (!Validator.checkInteger(drugId) || !Validator.checkDouble(priceDrug)){
            throw new ServiceCheckException(MessageConstant.WRONG_FORMAT);
        }
        int drugIdInt=Integer.parseInt(drugId);
        double priceDrugDouble=Double.parseDouble(priceDrug);

        PharmacistDAO pharmacistDAO = DAOFactory.getInstance().getPharmacistDAO();

        boolean checkDrug;
        try {
            checkDrug=pharmacistDAO.checkDrug(drugIdInt);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (!checkDrug){
            throw new ServiceCheckException(MessageConstant.DRUG_NOT_EXISTS);
        }

        try {
            pharmacistDAO.changePriceDrug(drugIdInt, priceDrugDouble);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }
    /**
     * Method validate data and create new drug in the database
     *
     * @param drugName name of drug
     * @param dosage dosage of drug
     * @param country manufacturer country of drug
     * @param priceDrug the price of drug
     * @param quantity the quantity of drug
     * @param recipe the code of drug recipe
     * @param categories the categories of drug
     * @throws ServiceException if DaoException is thrown
     * @throws ServiceCheckException if wrong parameters.
     */
    @Override
    public void addDrug(String drugName, String dosage, String country, String priceDrug, String quantity,
                        String recipe, String[] categories) throws ServiceException, ServiceCheckException {
        Validator.checkAddDrug(drugName, country, priceDrug, quantity, recipe, categories);
        if (!Validator.checkInteger(quantity) || !Validator.checkDouble(priceDrug)){
            throw new ServiceCheckException(MessageConstant.WRONG_FORMAT);
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
    /**
     * Method validate data and delete drug in the database
     *
     * @param drugId id of drug
     * @throws ServiceException if DaoException is thrown
     * @throws ServiceCheckException if wrong parameters or drug not exist.
     */
    @Override
    public void deleteDrug(String drugId) throws ServiceException, ServiceCheckException {
        if (!Validator.checkInteger(drugId)){
            throw new ServiceCheckException(MessageConstant.WRONG_FORMAT);
        }
        int drugIdInt=Integer.parseInt(drugId);

        PharmacistDAO pharmacistDAO = DAOFactory.getInstance().getPharmacistDAO();

        boolean checkDrug;
        try {
            checkDrug=pharmacistDAO.checkDrug(drugIdInt);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (!checkDrug){
            throw new ServiceCheckException(MessageConstant.DRUG_NOT_EXISTS);
        }

        try {
            pharmacistDAO.deleteDrug(drugIdInt);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    /**
     * Method validate data and add drug category in the database
     *
     * @param drugCategory name of drug category
     * @throws ServiceException if DaoException is thrown
     * @throws ServiceCheckException if wrong parameters or drug category exist.
     */
    @Override
    public void addDrugCategory(String drugCategory) throws ServiceException, ServiceCheckException {
        Validator.checkDrugCategory(drugCategory);

        PharmacistDAO pharmacistDAO = DAOFactory.getInstance().getPharmacistDAO();
        boolean checkDrugCategory;
        try {
            checkDrugCategory=pharmacistDAO.checkDrugCategory(drugCategory);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (checkDrugCategory){
            throw new ServiceCheckException(MessageConstant.DRUG_CATEGORY_EXIST);
        }

        try {
            pharmacistDAO.addDrugCategory(drugCategory);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    /**
     * Method validate data and delete drug category in the database
     *
     * @param drugCategory name of drug category
     * @throws ServiceException if DaoException is thrown
     * @throws ServiceCheckException if drug category not exist or not empty.
     */
    @Override
    public void deleteDrugCategory(String drugCategory) throws ServiceException, ServiceCheckException {

        PharmacistDAO pharmacistDAO = DAOFactory.getInstance().getPharmacistDAO();
        boolean checkDrugCategory;
        try {
            checkDrugCategory=pharmacistDAO.checkDrugCategory(drugCategory);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (!checkDrugCategory){
            throw new ServiceCheckException(MessageConstant.DRUG_CATEGORY_NOT_EXIST);
        }

        boolean checkDrugCategoryNotEmpty;
        try {
            checkDrugCategoryNotEmpty=pharmacistDAO.checkDrugCategoryNotEmpty(drugCategory);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (checkDrugCategoryNotEmpty){
            throw new ServiceCheckException(MessageConstant.DRUG_CATEGORY_NOT_EMPTY);
        }

        try {
            pharmacistDAO.deleteDrugCategory(drugCategory);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
