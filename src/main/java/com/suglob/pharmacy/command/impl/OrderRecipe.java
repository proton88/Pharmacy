package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.utils.ConstantClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderRecipe implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String result="";
        String drugName=request.getParameter(ConstantClass.DRUG_NAME);
        String doctorSurname=request.getParameter(ConstantClass.DOCTOR_SURNAME);
        User user= (User) request.getSession().getAttribute(ConstantClass.USER);
        int userId=user.getId();
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        ClientService service = factory.getClientService();
        ///////////////////////////////////////////////////////////////////////////////
        try {
            result = service.orderRecipe(drugName, doctorSurname, userId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.getSession().setAttribute("orderRecipe_msg",result);

        StringBuffer buf = (StringBuffer) request.getSession().getAttribute("url");
        String url = buf.toString();
        String urlParams = (String) request.getSession().getAttribute("urlParams");

        try {
            response.sendRedirect(url + "?" + urlParams);
        } catch (IOException e) {
            throw new CommandException("Don't execute url: " + url, e);
        }
    }
}