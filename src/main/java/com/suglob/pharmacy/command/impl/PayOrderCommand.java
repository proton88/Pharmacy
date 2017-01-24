package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.util.ConstantClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PayOrderCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        ClientService service = factory.getClientService();
        ///////////////////////////////////////////////////////////////////////////////
        String result;
        try {
            result = service.payOrder((List<Drug>) request.getSession().getAttribute(ConstantClass.ORDER_LIST));
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        request.getSession().setAttribute(ConstantClass.PAYMENT_MSG,result);
        request.getSession().setAttribute(ConstantClass.ORDER_LIST,null);
        request.getSession().setAttribute(ConstantClass.ORDER_PRICE, null);

        CommandHelp.sendResponse(request, response);
    }
}
