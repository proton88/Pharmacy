package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.util.ConstantClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderRecipeCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String result;
        String drugName=request.getParameter(ConstantClass.DRUG_NAME);
        String doctorSurname=request.getParameter(ConstantClass.DOCTOR_SURNAME);
        User user= (User) request.getSession().getAttribute(ConstantClass.USER);
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
        request.getSession().setAttribute(ConstantClass.MSG,result);

        CommandHelp.sendResponse(request, response);
    }
}
