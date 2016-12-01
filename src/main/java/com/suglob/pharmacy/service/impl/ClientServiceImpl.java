package com.suglob.pharmacy.service.impl;

import com.suglob.pharmacy.dao.CommonDAO;
import com.suglob.pharmacy.dao.DAOFactory;
import com.suglob.pharmacy.dao.UserDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.domain.User;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.service.exception.ServiceRegistrationException;
import com.suglob.pharmacy.service.utils.RegularChanges;

public class ClientServiceImpl implements ClientService{
    @Override
    public User registration(String login, String password, String passwordRepeat, String name, String surname, String patronymic,
                             String adress, String passportId) throws ServiceException, ServiceRegistrationException {
        User user=null;
        if(login == null || login.isEmpty() || password == null || password.isEmpty()){
            throw new ServiceRegistrationException("reg.login_password");
        }
        if(!password.equals(passwordRepeat)){
            throw new ServiceRegistrationException("reg.password");
        }
        if (!RegularChanges.passportChange(passportId)){
            throw new ServiceRegistrationException("reg.passport");
        }

        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO=factory.getUserDAO();
        CommonDAO commonDAO=factory.getCommonDAO();
        ////////////////////////////////////////////////////////////////////
        try {
            user=commonDAO.logination(login, password);
        } catch (DAOException e1) {
            throw new ServiceException(e1);
        }
        if(user!=null){
            throw new ServiceRegistrationException("reg.user");
        }
        user=null;
        //////////////////////////////////////////////////////////////////
        try{
            user=userDAO.registration(login, password, passwordRepeat, name, surname, patronymic, adress, passportId);
        }catch(DAOException e){
            throw new ServiceException(e);
        }
        return user;
    }
}
