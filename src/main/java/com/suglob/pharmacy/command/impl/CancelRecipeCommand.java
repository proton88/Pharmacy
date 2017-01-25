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

public class CancelRecipeCommand implements ICommand {
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
