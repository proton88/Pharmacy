package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.utils.CommandHelp;
import com.suglob.pharmacy.service.PharmacistService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.utils.ConstantClass;
import com.suglob.pharmacy.utils.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddQuantityDrug implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int drugId = Integer.parseInt(request.getParameter(ConstantClass.DRUG_ID));
        int currentQuantity = Integer.parseInt(request.getParameter(ConstantClass.CURRENT_QUANTITY));
        int drugQuantity=0;
        if (Validator.checkInteger(request.getParameter(ConstantClass.QUANTITY))){
            drugQuantity=Integer.parseInt(request.getParameter(ConstantClass.QUANTITY));
        }else{
            request.getSession().setAttribute("error", "recipeParameter.wrong");
            CommandHelp.sendResponse(request,response);
            return;
        }
        int newQuantity=currentQuantity+drugQuantity;
        if (newQuantity<0){
            newQuantity=0;
        }

        PharmacistService service=ServiceFactory.getInstance().getPharmacistService();

        try {
            service.addQuantityDrug(drugId, newQuantity);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        //request.getSession().setAttribute("msg", "add_drug.ok");
        CommandHelp.sendResponse(request,response);
    }
}