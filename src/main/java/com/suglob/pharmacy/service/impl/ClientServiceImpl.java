package com.suglob.pharmacy.service.impl;

import com.suglob.pharmacy.dao.CommonDAO;
import com.suglob.pharmacy.dao.DAOFactory;
import com.suglob.pharmacy.dao.UserDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.service.exception.ServiceRegistrationException;
import com.suglob.pharmacy.service.utils.RegularChanges;
import com.suglob.pharmacy.service.utils.Validator;

import java.util.List;

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

    @Override
    public String payOrder(List<Drug> orderList) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO=factory.getUserDAO();

        String result = null;
        try {
            result = userDAO.payOrder(orderList);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    @Override
    public int addRecipe(String recipeCode, int count, int id) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO=factory.getUserDAO();

        int result;
        try {
            result = userDAO.addRecipe(recipeCode, count, id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    @Override
    public void cancelOrder(int count, int id, int id_recipe) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO=factory.getUserDAO();

        try {
            userDAO.cancelOrder(count, id, id_recipe);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String orderRecipe(String drugName, String doctorSurname, int userId) throws ServiceException {
        String result=Validator.checkOrderRecipe(drugName, doctorSurname);

        if (result.equals("orderRecipe.ok")) {
            DAOFactory factory = DAOFactory.getInstance();
            UserDAO userDAO = factory.getUserDAO();

            try {
                userDAO.orderRecipe(drugName, doctorSurname, userId);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }

    @Override
    public String extendRecipe(String codeDrug) throws ServiceException {
        String result=Validator.checkExtendRecipe(codeDrug);

        if (result.equals("extendRecipe.ok")) {
            DAOFactory factory = DAOFactory.getInstance();
            UserDAO userDAO = factory.getUserDAO();

            try {
                userDAO.extendRecipe(codeDrug);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }
}
