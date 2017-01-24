package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.util.ConstantClass;
import com.suglob.pharmacy.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AddOrderCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int count;
        int id = Integer.parseInt(request.getParameter(ConstantClass.DRUG_ID));
        if (Validator.checkInteger(request.getParameter(ConstantClass.COUNT))){
            count=Integer.parseInt(request.getParameter(ConstantClass.COUNT));
        }else{
            request.getSession().setAttribute(ConstantClass.ERROR, ConstantClass.WRONG_FORMAT);
            CommandHelp.sendResponse(request,response);
            return;
        }
        String recipeCode=ConstantClass.EMPTY_STRING;
        if (request.getParameter(ConstantClass.CODE)!=null){
            recipeCode=request.getParameter(ConstantClass.CODE);
        }
        List<Drug> drugList = (List<Drug>) request.getSession().getAttribute(ConstantClass.LIST_DRUGS);
        BigDecimal price = (BigDecimal) request.getSession().getAttribute(ConstantClass.ORDER_PRICE);
        if (price == null) {
            price = new BigDecimal(ConstantClass.DOUBLE_ZERO);
        }
        List<Drug> orderList = (List<Drug>) request.getSession().getAttribute(ConstantClass.ORDER_LIST);
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        for (Drug drug : drugList) {
            if (drug.getId() == id) {
                if (count <= drug.getCount()) {
                    int idRecipes=ConstantClass.ZERO;
                    if (drug.getIsRecipe().equals(ConstantClass.Y)) {
                        ///////////////////////////////////////////////////////////////////////////////
                        ServiceFactory factory = ServiceFactory.getInstance();
                        ClientService service = factory.getClientService();
                        ///////////////////////////////////////////////////////////////////////////////
                        if (recipeCode.length()!=ConstantClass.RECIPE_CODE_LENGTH){
                            request.getSession().setAttribute(ConstantClass.ERROR, ConstantClass.BAD_RECIPE);
                            break;
                        }

                        try {
                            idRecipes = service.addRecipe(recipeCode, count, id);
                        } catch (ServiceException e) {
                            throw new CommandException(e);
                        }
                        if (idRecipes==ConstantClass.ZERO){
                            request.getSession().setAttribute(ConstantClass.ERROR, ConstantClass.BAD_RECIPE);
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
                            if (newDrug.getIsRecipe().equals(ConstantClass.Y)) {
                                newDrug.setIsRecipe(String.valueOf(idRecipes));
                            }
                            orderList.add(newDrug);
                        }
                    } else {
                        Drug newDrug = new Drug(drug);
                        newDrug.setCount(count);
                        if (newDrug.getIsRecipe().equals(ConstantClass.Y)) {
                            newDrug.setIsRecipe(String.valueOf(idRecipes));
                        }
                        orderList.add(newDrug);
                    }


                    request.getSession().setAttribute(ConstantClass.ORDER_LIST, orderList);
                    request.getSession().setAttribute(ConstantClass.ORDER_PRICE, price);
                } else {
                    request.getSession().setAttribute(ConstantClass.ERROR, ConstantClass.MSG_NOT_ENOUGH_DRUGS);
                }
                break;
            }
        }
        CommandHelp.sendResponse(request, response);
    }
}
