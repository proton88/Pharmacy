package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.utils.CommandHelp;
import com.suglob.pharmacy.service.PharmacistService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceCheckErrorException;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.utils.ConstantClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddDrug implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String drugName=request.getParameter(ConstantClass.DRUG_NAME);
        String dosage=request.getParameter(ConstantClass.DOSAGE);
        String country=request.getParameter(ConstantClass.COUNTRY);
        String priceDrug = request.getParameter(ConstantClass.PRICE_DRUG);
        String quantity = request.getParameter(ConstantClass.QUANTITY);
        String recipe = request.getParameter(ConstantClass.RECIPE);
        String[] categories =request.getParameterValues(ConstantClass.DRUG_CATEGORIES);



        PharmacistService service= ServiceFactory.getInstance().getPharmacistService();
        String error;
        try {
            service.addDrug(drugName, dosage, country, priceDrug, quantity, recipe.toUpperCase(), categories);
        } catch (ServiceCheckErrorException e){
            error=e.getMessage().trim();
            request.getSession().setAttribute(ConstantClass.ERROR, error);
            CommandHelp.sendResponse(request, response);
            return;
        }catch (ServiceException e){
            throw new CommandException(e);
        }
        request.getSession().setAttribute(ConstantClass.MSG, ConstantClass.MSG_ADD_DRUG_OK);
        CommandHelp.sendResponse(request, response);
    }
}
