package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.utils.CommandHelp;
import com.suglob.pharmacy.service.DoctorService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.utils.ConstantClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CancelExtendRecipe implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int positionRecipe=Integer.parseInt(request.getParameter(ConstantClass.POSITION));
        List<String> drugsCodeExtendRecipe = (List<String>) request.getSession().getAttribute("drugsCodeExtendRecipe");
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
