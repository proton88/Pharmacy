package com.suglob.pharmacy.utils;

import com.suglob.pharmacy.dao.DAOFactory;
import com.suglob.pharmacy.dao.DoctorDAO;
import com.suglob.pharmacy.dao.UserDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.service.exception.ServiceCheckErrorException;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.service.utils.RegularChanges;

public class Validator {

    public static String checkOrderRecipe(String drugName, String doctorSurname) throws ServiceException{
        String result="orderRecipe.ok";
        if (drugName.isEmpty() || doctorSurname==null){
            return result="orderRecipe.empty_field";
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO=factory.getUserDAO();

        String drugExists;
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

        String recipeExists;
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

    public static boolean checkInteger(String... params) {
        for (String param:params) {
            try {
                Integer.parseInt(param);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public static void checkAssignRecipe(int drugId, int quantity, int period, int clientId, String code)
            throws ServiceCheckErrorException, ServiceException {
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
            throw new ServiceCheckErrorException("assignRecipe.notDrugClient");
        }
        if (quantity>9 || quantity<=0){
            throw new ServiceCheckErrorException("assignRecipe.wrongQuantity");
        }
        if (period>60 || period<=0){
            throw new ServiceCheckErrorException("assignRecipe.wrongPeriod");
        }
        if (code.length()!=6){
            throw new ServiceCheckErrorException("assignRecipe.wrongCode");
        }
        boolean checkDrugCode;
        try {
            checkDrugCode=doctorDAO.checkDrugCode(code);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (!checkDrugCode){
            throw new ServiceCheckErrorException("assignRecipe.repeatCode");
        }
    }

    public static void checkRegistration(String login, String password, String passwordRepeat, String passportId)
            throws ServiceCheckErrorException{
        if(login == null || login.isEmpty() || password == null || password.isEmpty()){
            throw new ServiceCheckErrorException("reg.login_password");
        }
        if(!password.equals(passwordRepeat)){
            throw new ServiceCheckErrorException("reg.password");
        }
        if (!RegularChanges.passportChange(passportId)){
            throw new ServiceCheckErrorException("reg.passport");
        }
    }

    public static void checkPeriod(int period) throws ServiceCheckErrorException {
        if (period>60 || period<=0){
            throw new ServiceCheckErrorException("assignRecipe.wrongPeriod");
        }
    }
}
