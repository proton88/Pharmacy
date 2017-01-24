package com.suglob.pharmacy.util;

import com.suglob.pharmacy.dao.*;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.exception.ServiceCheckErrorException;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.service.util.RegularChanges;

public class Validator {

    public static String checkOrderRecipe(String drugName, String doctorSurname) throws ServiceException{
        String result=ConstantClass.ORDER_RECIPE_OK;
        if (drugName.isEmpty() || doctorSurname==null){
            return ConstantClass.ORDER_RECIPE_EMPTY_FIELD;
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO=factory.getUserDAO();

        String drugExists;
        try {
            drugExists = userDAO.drugExists(drugName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (drugExists.equals(ConstantClass.NOT_EXIST)){
            return ConstantClass.DRUG_NOT_EXIST;
        }
        if (drugExists.equals(ConstantClass.NOT_NEED)){
            return ConstantClass.DRUG_NOT_NEED;
        }

        return result;
    }

    public static String checkExtendRecipe(String codeDrug) throws ServiceException{
        String result=ConstantClass.EXTEND_RECIPE_OK;
        if (codeDrug.isEmpty()){
            return ConstantClass.EXTEND_RECIPE_EMPTY_FIELD;
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO=factory.getUserDAO();

        String recipeExists;
        try {
            recipeExists = userDAO.recipeExists(codeDrug);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (recipeExists.equals(ConstantClass.NOT_EXIST)){
            return ConstantClass.EXTEND_RECIPE_NOT_EXIST;
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
            throw new ServiceCheckErrorException(ConstantClass.NOT_DRUG_CLIENT);
        }
        if (quantity>ConstantClass.MAX_QUANTITY || quantity<=ConstantClass.ZERO){
            throw new ServiceCheckErrorException(ConstantClass.WRONG_QUANTITY);
        }
        if (period>ConstantClass.MAX_PERIOD || period<=ConstantClass.ZERO){
            throw new ServiceCheckErrorException(ConstantClass.WRONG_PERIOD);
        }
        if (code.length()!=ConstantClass.RECIPE_CODE_LENGTH){
            throw new ServiceCheckErrorException(ConstantClass.WRONG_CODE);
        }
        boolean checkDrugCode;
        try {
            checkDrugCode=doctorDAO.checkDrugCode(code);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (!checkDrugCode){
            throw new ServiceCheckErrorException(ConstantClass.REPEAT_CODE);
        }
    }

    public static void checkRegistration(String login, String password, String passwordRepeat, String name,
                                         String surname, String patronymic, String adress, String passportId,
                                         String email) throws ServiceCheckErrorException, ServiceException {
        if(login == null || login.isEmpty() || password == null || password.isEmpty() ||
                name == null || name.isEmpty() || surname == null || surname.isEmpty() ||
                adress == null || adress.isEmpty() || passportId == null || passportId.isEmpty() ||
                email == null || email.isEmpty()){
            throw new ServiceCheckErrorException(ConstantClass.REG_EMPTY_FIELD);
        }
        if (!RegularChanges.loginCheck(login)){
            throw new ServiceCheckErrorException(ConstantClass.REG_BAD_LOGIN);
        }
        if (!RegularChanges.passwordCheck(password)){
            throw new ServiceCheckErrorException(ConstantClass.REG_BAD_PASSWORD);
        }
        if(!password.equals(passwordRepeat)){
            throw new ServiceCheckErrorException(ConstantClass.REG_PASSWORD);
        }
        if (!RegularChanges.passportCheck(passportId)){
            throw new ServiceCheckErrorException(ConstantClass.REG_PASSPORT);
        }
        if (!RegularChanges.emailCheck(email)){
            throw new ServiceCheckErrorException(ConstantClass.REG_BAD_EMAIL);
        }
        User user;
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        CommonDAO commonDAO=factory.getCommonDAO();
        ////////////////////////////////////////////////////////////////////
        try {
            user=commonDAO.logination(login, password);
        } catch (DAOException e1) {
            throw new ServiceException(e1);
        }
        if(user!=null){
            throw new ServiceCheckErrorException(ConstantClass.REG_USER);
        }
    }

    public static void checkPeriod(int period) throws ServiceCheckErrorException {
        if (period>ConstantClass.MAX_PERIOD || period<=ConstantClass.ZERO){
            throw new ServiceCheckErrorException(ConstantClass.WRONG_PERIOD);
        }
    }

    public static boolean checkDouble(String... params) {
        for (String param:params) {
            try {
                Double.parseDouble(param);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public static void checkDrugExist(int drugIdInt) throws ServiceException, ServiceCheckErrorException {
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        PharmacistDAO pharmacistDAO=factory.getPharmacistDAO();
        ///////////////////////////////////////////////////
        boolean checkDrug;
        try {
            checkDrug=pharmacistDAO.checkDrug(drugIdInt);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (!checkDrug){
            throw new ServiceCheckErrorException(ConstantClass.DRUG_NOT_EXISTS);
        }
    }

    public static void checkAddDrug(String drugName, String dosage, String country, String priceDrug, String quantity, String recipe, String[] categories) throws ServiceCheckErrorException {
        if(drugName == null || drugName.isEmpty() || country == null || country.isEmpty() ||
                priceDrug == null || priceDrug.isEmpty() || quantity == null || quantity.isEmpty() ||
                recipe == null || recipe.isEmpty()){
            throw new ServiceCheckErrorException(ConstantClass.REG_EMPTY_FIELD);
        }
        if (recipe.compareToIgnoreCase(ConstantClass.Y)!=ConstantClass.ZERO &&
                recipe.compareToIgnoreCase(ConstantClass.N)!=ConstantClass.ZERO){
            throw new ServiceCheckErrorException(ConstantClass.WRONG_RECIPE_PARAM);
        }

        if (categories==null){
            throw new ServiceCheckErrorException(ConstantClass.EMPTY_CATEGORY);
        }
    }

    public static void checkDrugCategoryExist(String drugCategory) throws ServiceException, ServiceCheckErrorException {
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        PharmacistDAO pharmacistDAO=factory.getPharmacistDAO();
        ///////////////////////////////////////////////////
        boolean checkDrugCategory;
        try {
            checkDrugCategory=pharmacistDAO.checkDrugCategory(drugCategory);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (checkDrugCategory){
            throw new ServiceCheckErrorException(ConstantClass.DRUG_CATEGORY_EXIST);
        }
    }

    public static void checkDrugCategoryNotExist(String drugCategory) throws ServiceCheckErrorException, ServiceException {
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        PharmacistDAO pharmacistDAO=factory.getPharmacistDAO();
        ///////////////////////////////////////////////////
        boolean checkDrugCategory;
        try {
            checkDrugCategory=pharmacistDAO.checkDrugCategory(drugCategory);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (!checkDrugCategory){
            throw new ServiceCheckErrorException(ConstantClass.DRUG_CATEGORY_NOT_EXIST);
        }
    }

    public static void checkDrugCategoryNotEmpty(String drugCategory) throws ServiceException, ServiceCheckErrorException {
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        PharmacistDAO pharmacistDAO=factory.getPharmacistDAO();
        ///////////////////////////////////////////////////
        boolean checkDrugCategoryNotEmpty;
        try {
            checkDrugCategoryNotEmpty=pharmacistDAO.checkDrugCategoryNotEmpty(drugCategory);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        if (checkDrugCategoryNotEmpty){
            throw new ServiceCheckErrorException(ConstantClass.DRUG_CATEGORY_NOT_EMPTY);
        }
    }

    public static boolean checkLogination(String login, String password) {
        boolean result=true;
        if(login == null || login.isEmpty() || password == null || password.isEmpty()){
            result=false;
        }
        return  result;
    }

    public static void checkDrugCategory(String drugCategory) throws ServiceCheckErrorException {
        if (!RegularChanges.drugCategoryCheck(drugCategory)){
            throw new ServiceCheckErrorException(ConstantClass.REG_BAD_DRUG_CATEGORY);
        }
    }
}
