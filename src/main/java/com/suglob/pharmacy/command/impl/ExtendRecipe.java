package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.utils.ConstantClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExtendRecipe implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String codeDrug=request.getParameter(ConstantClass.CODE_DRUG);
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        ClientService service = factory.getClientService();
        ///////////////////////////////////////////////////////////////////////////////
        String result="";
        try {
            result = service.extendRecipe(codeDrug);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.getSession().setAttribute("extendRecipe_msg",result);

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
