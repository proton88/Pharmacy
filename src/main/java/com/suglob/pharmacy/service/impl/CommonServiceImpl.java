package com.suglob.pharmacy.service.impl;

import com.suglob.pharmacy.dao.CommonDAO;
import com.suglob.pharmacy.dao.DAOFactory;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.entity.DrugCategory;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.CommonService;
import com.suglob.pharmacy.service.exception.ServiceException;

import java.util.ArrayList;

public class CommonServiceImpl implements CommonService {

    @Override
    public User logination(String login, String password) throws ServiceException {
        User user=null;
        if(login == null || login.isEmpty() || password == null || password.isEmpty()){
            return user;
        }
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        CommonDAO commonDAO=factory.getCommonDAO();
        ///////////////////////////////////////////////////
        try{
            user=commonDAO.logination(login, password);
        }catch(DAOException e){
            throw new ServiceException(e.toString(),e);
        }
        return user;
    }

    @Override
    public ArrayList<DrugCategory> takeDrugCategories() throws ServiceException {
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        CommonDAO commonDAO=factory.getCommonDAO();
        ///////////////////////////////////////////////////
        ArrayList<DrugCategory> drugCategoriesList = new ArrayList<>();

        try {
            drugCategoriesList=commonDAO.takeDrugCategories();
        } catch (DAOException e) {
            throw new ServiceException(e.toString(),e);
        }

        return drugCategoriesList;
    }

    @Override
    public ArrayList<Drug> takeDrugs(String str) throws ServiceException {
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        CommonDAO commonDAO=factory.getCommonDAO();
        ///////////////////////////////////////////////////
        ArrayList<Drug> drugList = new ArrayList<>();

        try {
            drugList=commonDAO.takeDrugs(str);
        } catch (DAOException e) {
            throw new ServiceException(e.toString(),e);
        }

        return drugList;
    }
}