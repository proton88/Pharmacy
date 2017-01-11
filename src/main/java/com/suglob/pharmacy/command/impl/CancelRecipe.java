package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.utils.CommandHelp;
import com.suglob.pharmacy.entity.Client;
import com.suglob.pharmacy.service.DoctorService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.utils.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CancelRecipe implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String drugName=request.getParameter("drugName");
        int clientId=0;
        if (Validator.checkInteger(request.getParameter("clientId"))) {
            clientId = Integer.parseInt(request.getParameter("clientId"));
        }else{
            request.getSession().setAttribute("error", "clientId.wrong");
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
