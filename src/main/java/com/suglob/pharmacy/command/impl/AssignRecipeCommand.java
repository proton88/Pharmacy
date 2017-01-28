package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.service.DoctorService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceCheckException;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This class is for adding recipe in the database
 */
public class AssignRecipeCommand implements ICommand {
    /**
     * This method retrieves from request recipe parameters and transmits to the service layer for adding recipe in database.
     * If there was an error in a parameter, displays it on the page.
     * Also method delete order recipe from list.
     *
     * @param request for receiving the transmitted data
     * @param response to generate a response
     * @throws CommandException if ServiceException is thrown
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int userId = Integer.parseInt(request.getParameter(OtherConstant.USER_ID));
        String sDrugId=request.getParameter(OtherConstant.DRUG_ID);
        String sQuantity=request.getParameter(OtherConstant.QUANTITY);
        String sPeriod=request.getParameter(OtherConstant.PERIOD);
        String sClientId=request.getParameter(OtherConstant.CLIENT_ID);
        String code=request.getParameter(OtherConstant.CODE);
        int clientId;
        int drugId;
        int quantity;
        int period;
        if (Validator.checkInteger(sDrugId, sQuantity, sPeriod, sClientId)) {
            clientId = Integer.parseInt(sClientId);
            drugId = Integer.parseInt(sDrugId);
            quantity = Integer.parseInt(sQuantity);
            period = Integer.parseInt(sPeriod);
        }else{
            request.getSession().setAttribute(OtherConstant.ERROR, MessageConstant.MSG_RECIPE_PARAMETER_WRONG);
            CommandHelp.sendResponse(request, response);
            return;
        }

        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        DoctorService service = factory.getDoctorService();
        ///////////////////////////////////////////////////////////////////////////////
        String error;
        String drugName;
        try {
            drugName=service.assignRecipe(userId,drugId, quantity, period, clientId, code);
        } catch (ServiceCheckException e){
            error=e.getMessage().trim();
            request.getSession().setAttribute(OtherConstant.ERROR, error);
            CommandHelp.sendResponse(request, response);
            return;
        }catch (ServiceException e){
            throw new CommandException(e);
        }
        CommandHelp.clearOrderRecipe(request, drugName, clientId);
        CommandHelp.sendResponse(request, response);
    }
}
