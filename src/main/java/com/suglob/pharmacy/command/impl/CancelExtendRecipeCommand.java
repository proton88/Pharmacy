package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.service.DoctorService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * This class is for cancel extend recipe.
 */
public class CancelExtendRecipeCommand implements ICommand {
    /**
     * This method retrieves from request recipe parameters and transmits to the service layer for remove order in database.
     * Also method delete order on extend recipe from list.
     *
     * @param request for receiving the transmitted data
     * @param response to generate a response
     * @throws CommandException if ServiceException is thrown
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int positionRecipe=Integer.parseInt(request.getParameter(OtherConstant.POSITION));
        List<String> drugsCodeExtendRecipe = (List<String>) request.getSession().getAttribute(OtherConstant.DRUGS_CODE_EXTEND_RECIPE);
        String codeRecipe = drugsCodeExtendRecipe.get(positionRecipe-1);
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        DoctorService service = factory.getDoctorService();
        ///////////////////////////////////////////////////////////////////////////////
        try {
            service.cancelExtendRecipe(codeRecipe);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandHelp.clearExtendRecipe(request, positionRecipe);

        CommandHelp.sendResponse(request, response);
    }
}
