package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.constant.NumberConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * This class is for adding drugs in the order
 */
public class AddOrderCommand implements ICommand {
    /**
     * This method retrieves from request drug parameters and transmits to the service layer for adding.
     * Also method handles basket orders.
     * If there was an error in a parameter, displays it on the page.
     *
     * @param request for receiving the transmitted data
     * @param response to generate a response
     * @throws CommandException if ServiceException is thrown
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int count;
        int id = Integer.parseInt(request.getParameter(OtherConstant.DRUG_ID));
        if (Validator.checkInteger(request.getParameter(OtherConstant.COUNT))){
            count=Integer.parseInt(request.getParameter(OtherConstant.COUNT));
        }else{
            request.getSession().setAttribute(OtherConstant.ERROR, MessageConstant.WRONG_FORMAT);
            CommandHelp.sendResponse(request,response);
            return;
        }
        String recipeCode= OtherConstant.EMPTY_STRING;
        if (request.getParameter(OtherConstant.CODE)!=null){
            recipeCode=request.getParameter(OtherConstant.CODE);
        }
        List<Drug> drugList = (List<Drug>) request.getSession().getAttribute(OtherConstant.LIST_DRUGS);
        BigDecimal price = (BigDecimal) request.getSession().getAttribute(OtherConstant.ORDER_PRICE);
        if (price == null) {
            price = new BigDecimal(NumberConstant.DOUBLE_ZERO);
        }
        List<Drug> orderList = (List<Drug>) request.getSession().getAttribute(OtherConstant.ORDER_LIST);
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        for (Drug drug : drugList) {
            if (drug.getId() == id) {
                if (count <= drug.getCount()) {
                    int idRecipes= NumberConstant.ZERO;
                    if (OtherConstant.Y.equals(drug.getIsRecipe())) {
                        ///////////////////////////////////////////////////////////////////////////////
                        ServiceFactory factory = ServiceFactory.getInstance();
                        ClientService service = factory.getClientService();
                        ///////////////////////////////////////////////////////////////////////////////
                        if (recipeCode.length()!= NumberConstant.RECIPE_CODE_LENGTH){
                            request.getSession().setAttribute(OtherConstant.ERROR, MessageConstant.BAD_RECIPE);
                            break;
                        }

                        try {
                            idRecipes = service.addOrder(recipeCode, count, id);
                        } catch (ServiceException e) {
                            throw new CommandException(e);
                        }
                        if (idRecipes== NumberConstant.ZERO){
                            request.getSession().setAttribute(OtherConstant.ERROR, MessageConstant.BAD_RECIPE);
                            break;
                        }
                    }
                    price = price.add(drug.getPrice().multiply(new BigDecimal(count)));
                    if (!orderList.isEmpty()) {
                        boolean isNewDrug=true;
                        for (Drug orderDrug : orderList) {
                            if (orderDrug.getId() == id) {
                                orderDrug.setCount(orderDrug.getCount() + count);
                                isNewDrug=false;
                            }
                        }
                        if (isNewDrug) {
                            Drug newDrug = new Drug(drug);
                            newDrug.setCount(count);
                            if (OtherConstant.Y.equals(newDrug.getIsRecipe())) {
                                newDrug.setIsRecipe(String.valueOf(idRecipes));
                            }
                            orderList.add(newDrug);
                        }
                    } else {
                        Drug newDrug = new Drug(drug);
                        newDrug.setCount(count);
                        if (OtherConstant.Y.equals(newDrug.getIsRecipe())) {
                            newDrug.setIsRecipe(String.valueOf(idRecipes));
                        }
                        orderList.add(newDrug);
                    }


                    request.getSession().setAttribute(OtherConstant.ORDER_LIST, orderList);
                    request.getSession().setAttribute(OtherConstant.ORDER_PRICE, price);
                } else {
                    request.getSession().setAttribute(OtherConstant.ERROR, MessageConstant.MSG_NOT_ENOUGH_DRUGS);
                }
                break;
            }
        }
        CommandHelp.sendResponse(request, response);
    }
}
