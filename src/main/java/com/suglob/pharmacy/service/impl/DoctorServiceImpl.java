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

public class DoctorServiceImpl implements DoctorService {
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
