package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This class is for ordering recipe.
 */
public class OrderRecipeCommand implements ICommand {
    /**
     * This method retrieves from request required parameters and transmits to the service layer
     * for add order for recipe in database.
     * Result of operation display on the page.
     *
     * @param request for receiving the transmitted data
     * @param response to generate a response
     * @throws CommandException if ServiceException is thrown
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String result;
        String drugName=request.getParameter(OtherConstant.DRUG_NAME);
        String doctorSurname=request.getParameter(OtherConstant.DOCTOR_SURNAME);
        User user= (User) request.getSession().getAttribute(OtherConstant.USER);
        int userId;
        if (user!=null) {
            userId = user.getId();
        }else{
            throw new CommandException("don't user");
        }
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        ClientService service = factory.getClientService();
        ///////////////////////////////////////////////////////////////////////////////
        try {
            result = service.orderRecipe(drugName, doctorSurname, userId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.getSession().setAttribute(OtherConstant.MSG,result);

        CommandHelp.sendResponse(request, response);
    }
}
