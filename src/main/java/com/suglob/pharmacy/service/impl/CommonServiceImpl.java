package com.suglob.pharmacy.service.impl;

import com.suglob.pharmacy.dao.CommonDAO;
import com.suglob.pharmacy.dao.DAOFactory;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.entity.Doctor;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.entity.DrugCategory;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.CommonService;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.validation.Validator;

import java.util.ArrayList;
/**
 * This class contains methods to verify the data and transmit them to the DAO layer.
 * All methods are associated with the all users.
 */
public class CommonServiceImpl implements CommonService {
    private int countRecords;
    /**
     * Executes validation and returns user after log in.
     *
     * @param login unique user login
     * @param password unique user password
     * @return user if the authentication is successful
     * @throws ServiceException if DaoException is thrown
     */
    @Override
    public User logination(String login, String password) throws ServiceException {
        User user=null;
        if (!Validator.checkLogination(login, password)){
            return user;
        }

        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        CommonDAO commonDAO=factory.getCommonDAO();
        ///////////////////////////////////////////////////
        try{
            user=commonDAO.logination(login, password);
        }catch(DAOException e){
            throw new ServiceException(e);
        }
        return user;
    }
    /**
     * Get list of drug categories
     *
     * @return list of drug categories
     * @throws ServiceException if DaoException is thrown
     */
    @Override
    public ArrayList<DrugCategory> takeDrugCategories() throws ServiceException {
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        CommonDAO commonDAO=factory.getCommonDAO();
        ///////////////////////////////////////////////////
        ArrayList<DrugCategory> drugCategoriesList;

        try {
            drugCategoriesList=commonDAO.takeDrugCategories();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return drugCategoriesList;
    }
    /**
     * Get list of drugs
     *
     * @param str sql string for different sets of drugs
     * @return list of drugs
     * @throws ServiceException if DaoException is thrown
     */
    @Override
    public ArrayList<Drug> takeDrugs(String str) throws ServiceException {
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        CommonDAO commonDAO=factory.getCommonDAO();
        ///////////////////////////////////////////////////
        ArrayList<Drug> drugList;

        try {
            drugList=commonDAO.takeDrugs(str);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        countRecords=commonDAO.getCountRecords();
        return drugList;
    }

    public int getCountRecords() {
        return countRecords;
    }
    /**
     * Get list of all doctors
     *
     * @return list of all doctors
     * @throws ServiceException if DaoException is thrown
     */
    @Override
    public ArrayList<Doctor> takeDoctors() throws ServiceException {
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        CommonDAO commonDAO=factory.getCommonDAO();
        ///////////////////////////////////////////////////
        ArrayList<Doctor> doctorsList;

        try {
            doctorsList=commonDAO.takeDoctors();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return doctorsList;
    }
}
