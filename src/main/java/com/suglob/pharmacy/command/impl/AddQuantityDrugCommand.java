package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.constant.NumberConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.service.PharmacistService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddQuantityDrugCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int drugId = Integer.parseInt(request.getParameter(OtherConstant.DRUG_ID));
        int currentQuantity = Integer.parseInt(request.getParameter(OtherConstant.CURRENT_QUANTITY));
        int drugQuantity;
        if (Validator.checkInteger(request.getParameter(OtherConstant.QUANTITY))){
            drugQuantity=Integer.parseInt(request.getParameter(OtherConstant.QUANTITY));
        }else{
            request.getSession().setAttribute(OtherConstant.ERROR, MessageConstant.MSG_RECIPE_PARAMETER_WRONG);
            CommandHelp.sendResponse(request,response);
            return;
        }
        int newQuantity=currentQuantity+drugQuantity;
        if (newQuantity< NumberConstant.ZERO){
            newQuantity= NumberConstant.ZERO;
        }

        PharmacistService service=ServiceFactory.getInstance().getPharmacistService();

        try {
            service.addQuantityDrug(drugId, newQuantity);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandHelp.sendResponse(request,response);
    }
}
