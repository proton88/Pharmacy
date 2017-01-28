package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.service.DoctorService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This class is for to cancel the order for the recipe.
 */
public class CancelRecipeCommand implements ICommand {
    /**
     * This method retrieves from request recipe parameters and transmits to the service layer
     * for removal order for the recipe in database.
     * If there was an error in a parameter, displays it on the page.
     * Also method delete order for the recipe from list.
     *
     * @param request for receiving the transmitted data
     * @param response to generate a response
     * @throws CommandException if ServiceException is thrown
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int userId = Integer.parseInt(request.getParameter(OtherConstant.USER_ID));
        String drugName=request.getParameter(OtherConstant.DRUG_NAME);
        int clientId;
        if (Validator.checkInteger(request.getParameter(OtherConstant.CLIENT_ID))) {
            clientId = Integer.parseInt(request.getParameter(OtherConstant.CLIENT_ID));
        }else{
            request.getSession().setAttribute(OtherConstant.ERROR, MessageConstant.MSG_CLIENT_ID_WRONG);
            CommandHelp.sendResponse(request, response);
            return;
        }
        CommandHelp.clearOrderRecipe(request, drugName, clientId);

        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        DoctorService service = factory.getDoctorService();
        ///////////////////////////////////////////////////////////////////////////////
        try {
            service.cancelRecipe(userId, drugName, clientId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandHelp.sendResponse(request, response);
    }
}
