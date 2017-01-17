package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.utils.CommandHelp;
import com.suglob.pharmacy.service.DoctorService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.utils.ConstantClass;
import com.suglob.pharmacy.utils.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelRecipe implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int userId = Integer.parseInt(request.getParameter(ConstantClass.USER_ID));
        String drugName=request.getParameter(ConstantClass.DRUG_NAME);
        int clientId;
        if (Validator.checkInteger(request.getParameter(ConstantClass.CLIENT_ID))) {
            clientId = Integer.parseInt(request.getParameter(ConstantClass.CLIENT_ID));
        }else{
            request.getSession().setAttribute(ConstantClass.ERROR, ConstantClass.MSG_CLIENT_ID_WRONG);
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
