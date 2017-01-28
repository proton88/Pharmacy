package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This class is for order for extend recipe.
 */
public class OrderExtendRecipeCommand implements ICommand {
    /**
     * This method retrieves from request code drug and transmits to the service layer
     * for add order for extend recipe in database .
     * Result of operation display on the page.
     *
     * @param request for receiving the transmitted data
     * @param response to generate a response
     * @throws CommandException if ServiceException is thrown
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String codeDrug=request.getParameter(OtherConstant.CODE_DRUG);
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        ClientService service = factory.getClientService();
        ///////////////////////////////////////////////////////////////////////////////
        String result;
        try {
            result = service.orderExtendRecipe(codeDrug);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.getSession().setAttribute(OtherConstant.MSG,result);

        CommandHelp.sendResponse(request, response);
    }
}
