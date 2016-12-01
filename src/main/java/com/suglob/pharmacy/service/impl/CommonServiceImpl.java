package com.suglob.pharmacy.service.impl;

import com.suglob.pharmacy.dao.CommonDAO;
import com.suglob.pharmacy.dao.DAOFactory;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.domain.User;
import com.suglob.pharmacy.service.CommonService;
import com.suglob.pharmacy.service.exception.ServiceException;

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
            throw new ServiceException(e);
        }
        return user;
    }
}
