package com.suglob.pharmacy.service.impl;

import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.dao.DAOFactory;
import com.suglob.pharmacy.dao.DoctorDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.service.DoctorService;
import com.suglob.pharmacy.service.exception.ServiceCheckException;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.validation.Validator;

import java.util.List;
import java.util.Map;
/**
 * This class contains methods to verify the data and transmit them to the DAO layer.
 * All methods are associated with the user: 'doctor'.
 */
public class DoctorServiceImpl implements DoctorService {
    /**
     * Method get a map containing the orders for the extension or getting of recipe
     *
     * @param userId unique id user who wants to check orders for recipe
     * @return map containing the orders for the extension or getting of recipe
     * @throws ServiceException if DaoException is thrown
     */
    @Override
    public Map<String, List> checkRecipe(int userId) throws ServiceException {
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        DoctorDAO doctorDAO=factory.getDoctorDAO();
        ///////////////////////////////////////////////////
        Map<String, List> result;

        try {
            result=doctorDAO.checkRecipe(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return result;
    }
    /**
     * Method cancel recipe
     *
     * @param userId unique id user who wants to check orders for recipe
     * @param drugName the name of the drug, a recipe for which it is necessary to cancel
     * @param clientId unique id client whose order should be canceled
     * @throws ServiceException if DaoException is thrown
     */
    @Override
    public void cancelRecipe(int userId, String drugName, int clientId) throws ServiceException {
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        DoctorDAO doctorDAO=factory.getDoctorDAO();
        ///////////////////////////////////////////////////

        try {
            doctorDAO.cancelRecipe(userId, drugName, clientId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    /**
     * Method validate parameters and assign recipe
     *
     * @param userId unique id user who wants to assign the recipe
     * @param drugId id drug that it is necessary assign
     * @param quantity drug quantity that it is necessary assign
     * @param period period of appointment drug
     * @param clientId unique id client whose recipe should be assigned
     * @param code code of drug that it is necessary assign
     * @return the name of the drug, which is assigned to the recipe
     * @throws ServiceException if DaoException is thrown
     * @throws ServiceCheckException if wrong parameters.
     */
    @Override
    public String assignRecipe(int userId, int drugId, int quantity, int period, int clientId, String code) throws ServiceException, ServiceCheckException {
        Validator.checkAssignRecipe(quantity, period, code);
        String drugName;
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        DoctorDAO doctorDAO=factory.getDoctorDAO();
        ///////////////////////////////////////////////////

        boolean checkDrugAndClient;
        try {
            checkDrugAndClient=doctorDAO.checkDrugAndClient(drugId, clientId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (!checkDrugAndClient){
            throw new ServiceCheckException(MessageConstant.NOT_DRUG_CLIENT);
        }

        boolean checkDrugCode;
        try {
            checkDrugCode=doctorDAO.checkDrugCode(code);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (!checkDrugCode){
            throw new ServiceCheckException(MessageConstant.REPEAT_CODE);
        }

        try {
            drugName= doctorDAO.assignRecipe(userId, drugId, quantity, period, clientId, code);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return drugName;
    }
    /**
     * Method cancel extend recipe
     *
     * @param codeRecipe recipe code, which must not extend
     * @throws ServiceException if DaoException is thrown
     */
    @Override
    public void cancelExtendRecipe(String codeRecipe) throws ServiceException {
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        DoctorDAO doctorDAO=factory.getDoctorDAO();
        ///////////////////////////////////////////////////

        try {
            doctorDAO.cancelExtendRecipe(codeRecipe);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    /**
     * Method extend recipe
     *
     * @param codeRecipe recipe code, which must extend
     * @param period period of extending recipe
     * @throws ServiceException if DaoException is thrown
     * @throws ServiceCheckException if wrong parameter.
     */
    @Override
    public void extendRecipe(int period, String codeRecipe) throws ServiceException, ServiceCheckException {
        Validator.checkPeriod(period);
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        DoctorDAO doctorDAO=factory.getDoctorDAO();
        ///////////////////////////////////////////////////

        try {
            doctorDAO.extendRecipe(period, codeRecipe);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
