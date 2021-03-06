package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.service.PharmacistService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceCheckException;
import com.suglob.pharmacy.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This class is for adding drug
 */
public class AddDrugCommand implements ICommand {
    /**
     * This method retrieves from request drug parameters and transmits to the service layer for adding.
     * If there was an error in a parameter, displays it on the page.
     * If the operation is successful, it displays a success message to the screen.
     *
     * @param request for receiving the transmitted data
     * @param response to generate a response
     * @throws CommandException if ServiceException is thrown
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String drugName=request.getParameter(OtherConstant.DRUG_NAME);
        String dosage=request.getParameter(OtherConstant.DOSAGE);
        String country=request.getParameter(OtherConstant.COUNTRY);
        String priceDrug = request.getParameter(OtherConstant.PRICE_DRUG);
        String quantity = request.getParameter(OtherConstant.QUANTITY);
        String recipe = request.getParameter(OtherConstant.RECIPE);
        String[] categories =request.getParameterValues(OtherConstant.DRUG_CATEGORIES);



        PharmacistService service= ServiceFactory.getInstance().getPharmacistService();
        String error;
        try {
            service.addDrug(drugName, dosage, country, priceDrug, quantity, recipe.toUpperCase(), categories);
        } catch (ServiceCheckException e){
            error=e.getMessage().trim();
            request.getSession().setAttribute(OtherConstant.ERROR, error);
            CommandHelp.sendResponse(request, response);
            return;
        }catch (ServiceException e){
            throw new CommandException(e);
        }
        request.getSession().setAttribute(OtherConstant.MSG, MessageConstant.MSG_ADD_DRUG_OK);
        CommandHelp.sendResponse(request, response);
    }
}
