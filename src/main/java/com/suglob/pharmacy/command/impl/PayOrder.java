package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PayOrder implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        ClientService service = factory.getClientService();
        ///////////////////////////////////////////////////////////////////////////////\
        String result;
        try {
            result = service.payOrder((List<Drug>) request.getSession().getAttribute("orderList"));
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        request.getSession().setAttribute("payment_msg",result);
        request.getSession().setAttribute("orderList",null);
        request.getSession().setAttribute("orderPrice", null);

        StringBuffer buf= (StringBuffer) request.getSession().getAttribute("url");
        String url=buf.toString();
        String urlParams = (String) request.getSession().getAttribute("urlParams");

        try {
            response.sendRedirect(url+"?"+urlParams);
        } catch (IOException e) {
            throw new CommandException("Don't execute url: "+url,e);
        }
    }
}
