package com.suglob.pharmacy.service.utils;

import com.suglob.pharmacy.dao.DAOFactory;
import com.suglob.pharmacy.dao.UserDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.service.exception.ServiceException;

public class Validator {

    public static String checkOrderRecipe(String drugName, String doctorSurname) throws ServiceException{
        String result="orderRecipe.ok";
        if (drugName.isEmpty() || doctorSurname==null){
            return result="orderRecipe.empty_field";
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO=factory.getUserDAO();

        String drugExists= "";
        try {
            drugExists = userDAO.drugExists(drugName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (drugExists.equals("not exists")){
            return result="orderRecipe.drug_not_exists";
        }
        if (drugExists.equals("not need")){
            return result="orderRecipe.drug_not_need";
        }

        return result;
    }

    public static String checkExtendRecipe(String codeDrug) throws ServiceException{
        String result="extendRecipe.ok";
        if (codeDrug.isEmpty()){
            return result="extendRecipe.empty_field";
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO=factory.getUserDAO();

        String recipeExists= "";
        try {
            recipeExists = userDAO.recipeExists(codeDrug);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (recipeExists.equals("not exists")){
            return result="extendRecipe.recipe_not_exists";
        }

        return result;
    }
}
