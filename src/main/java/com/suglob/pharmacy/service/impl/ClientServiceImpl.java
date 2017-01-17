package com.suglob.pharmacy.service.impl;

import com.suglob.pharmacy.dao.DAOFactory;
import com.suglob.pharmacy.dao.UserDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.service.exception.ServiceCheckErrorException;
import com.suglob.pharmacy.utils.ConstantClass;
import com.suglob.pharmacy.utils.Validator;

import java.util.List;

public class ClientServiceImpl implements ClientService{
    @Override
    public User registration(String login, String password, String passwordRepeat, String name, String surname, String patronymic,
                             String adress, String passportId, String email) throws ServiceException, ServiceCheckErrorException {

        Validator.checkRegistration(login, password, passwordRepeat, name, surname, patronymic, adress, passportId, email);

        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO=factory.getUserDAO();
        ////////////////////////////////////////////////////////////////////

        User user;
        try{
            user=userDAO.registration(login, password, passwordRepeat, name, surname, patronymic, adress, passportId, email);
        }catch(DAOException e){
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public String payOrder(List<Drug> orderList) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO=factory.getUserDAO();

        String result;
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
    public void cancelOrder(int count, int id, int idRecipe) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO=factory.getUserDAO();

        try {
            userDAO.cancelOrder(count, id, idRecipe);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String orderRecipe(String drugName, String doctorSurname, int userId) throws ServiceException {
        String result=Validator.checkOrderRecipe(drugName, doctorSurname);

        if (result.equals(ConstantClass.ORDER_RECIPE_OK)) {
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
    public String orderExtendRecipe(String codeDrug) throws ServiceException {
        String result=Validator.checkExtendRecipe(codeDrug);

        if (result.equals(ConstantClass.EXTEND_RECIPE_OK)) {
            DAOFactory factory = DAOFactory.getInstance();
            UserDAO userDAO = factory.getUserDAO();

            try {
                userDAO.orderExtendRecipe(codeDrug);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }
}
