package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.utils.CommandHelp;
import com.suglob.pharmacy.service.DoctorService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceCheckErrorException;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.utils.ConstantClass;
import com.suglob.pharmacy.utils.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ExtendRecipe implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int positionRecipe=Integer.parseInt(request.getParameter(ConstantClass.POSITION));
        Validator.checkInteger(request.getParameter(ConstantClass.PERIOD));
        int period=Integer.parseInt(request.getParameter(ConstantClass.PERIOD));
        List<String> drugsCodeExtendRecipe = (List<String>) request.getSession().getAttribute("drugsCodeExtendRecipe");
        String codeRecipe = drugsCodeExtendRecipe.get(positionRecipe-1);

        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        DoctorService service = factory.getDoctorService();
        ///////////////////////////////////////////////////////////////////////////////
        String error="";
        try {
            service.cancelExtendRecipe(codeRecipe);
            service.extendRecipe(period, codeRecipe);
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (ServiceCheckErrorException e) {
            error=e.getMessage().trim();
            request.getSession().setAttribute("error", error);
            CommandHelp.sendResponse(request, response);
            return;
        }
        CommandHelp.clearExtendRecipe(request, positionRecipe);
        request.getSession().setAttribute("extendRecipe_msg","extend_recipe.ok");
        CommandHelp.sendResponse(request, response);
    }
}
