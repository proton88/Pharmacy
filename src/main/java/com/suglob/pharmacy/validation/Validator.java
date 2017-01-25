package com.suglob.pharmacy.validation;

import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.constant.NumberConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.service.exception.ServiceCheckException;
import com.suglob.pharmacy.service.util.RegularChanges;

public class Validator {

    public static String checkOrderRecipe(String drugName, String doctorSurname){
        String result= MessageConstant.ORDER_RECIPE_OK;
        if (drugName.isEmpty() || doctorSurname==null){
            return MessageConstant.ORDER_RECIPE_EMPTY_FIELD;
        }
        return result;
    }

    public static String checkExtendRecipe(String codeDrug){
        String result= MessageConstant.EXTEND_RECIPE_OK;
        if (codeDrug.isEmpty()){
            return MessageConstant.EXTEND_RECIPE_EMPTY_FIELD;
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

    public static void checkAssignRecipe(int quantity, int period, String code)
            throws ServiceCheckException{
        if (quantity> NumberConstant.MAX_QUANTITY || quantity<= NumberConstant.ZERO){
            throw new ServiceCheckException(MessageConstant.WRONG_QUANTITY);
        }
        if (period> NumberConstant.MAX_PERIOD || period<= NumberConstant.ZERO){
            throw new ServiceCheckException(MessageConstant.WRONG_PERIOD);
        }
        if (code.length()!= NumberConstant.RECIPE_CODE_LENGTH){
            throw new ServiceCheckException(MessageConstant.WRONG_CODE);
        }
    }

    public static void checkRegistration(String login, String password, String passwordRepeat, String name,
                                         String surname, String adress, String passportId,
                                         String email) throws ServiceCheckException {
        if(login == null || login.isEmpty() || password == null || password.isEmpty() ||
                name == null || name.isEmpty() || surname == null || surname.isEmpty() ||
                adress == null || adress.isEmpty() || passportId == null || passportId.isEmpty() ||
                email == null || email.isEmpty()){
            throw new ServiceCheckException(MessageConstant.REG_EMPTY_FIELD);
        }
        if (!RegularChanges.loginCheck(login)){
            throw new ServiceCheckException(MessageConstant.REG_BAD_LOGIN);
        }
        if (!RegularChanges.passwordCheck(password)){
            throw new ServiceCheckException(MessageConstant.REG_BAD_PASSWORD);
        }
        if(!password.equals(passwordRepeat)){
            throw new ServiceCheckException(MessageConstant.REG_PASSWORD);
        }
        if (!RegularChanges.passportCheck(passportId)){
            throw new ServiceCheckException(MessageConstant.REG_PASSPORT);
        }
        if (!RegularChanges.emailCheck(email)){
            throw new ServiceCheckException(MessageConstant.REG_BAD_EMAIL);
        }
    }

    public static void checkPeriod(int period) throws ServiceCheckException {
        if (period> NumberConstant.MAX_PERIOD || period<= NumberConstant.ZERO){
            throw new ServiceCheckException(MessageConstant.WRONG_PERIOD);
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

    public static void checkAddDrug(String drugName, String country, String priceDrug, String quantity, String recipe, String[] categories) throws ServiceCheckException {
        if(drugName == null || drugName.isEmpty() || country == null || country.isEmpty() ||
                priceDrug == null || priceDrug.isEmpty() || quantity == null || quantity.isEmpty() ||
                recipe == null || recipe.isEmpty()){
            throw new ServiceCheckException(MessageConstant.REG_EMPTY_FIELD);
        }
        if (recipe.compareToIgnoreCase(OtherConstant.Y)!= NumberConstant.ZERO &&
                recipe.compareToIgnoreCase(OtherConstant.N)!= NumberConstant.ZERO){
            throw new ServiceCheckException(MessageConstant.WRONG_RECIPE_PARAM);
        }

        if (categories==null){
            throw new ServiceCheckException(MessageConstant.EMPTY_CATEGORY);
        }
    }


    public static boolean checkLogination(String login, String password) {
        boolean result=true;
        if(login == null || login.isEmpty() || password == null || password.isEmpty()){
            result=false;
        }
        return  result;
    }

    public static void checkDrugCategory(String drugCategory) throws ServiceCheckException {
        if (!RegularChanges.drugCategoryCheck(drugCategory)){
            throw new ServiceCheckException(MessageConstant.REG_BAD_DRUG_CATEGORY);
        }
    }
}
