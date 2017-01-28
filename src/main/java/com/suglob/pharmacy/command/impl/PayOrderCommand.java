package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * This class is for paying order.
 */
public class PayOrderCommand implements ICommand {
    /**
     * This method retrieves from request list of ordered drugs and transmits to the service layer
     * for changing data in the database.
     * Also method clear list of ordered drugs.
     * Result of operation display on the page.
     *
     * @param request for receiving the transmitted data
     * @param response to generate a response
     * @throws CommandException if ServiceException is thrown
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        ClientService service = factory.getClientService();
        ///////////////////////////////////////////////////////////////////////////////
        String result;
        try {
            result = service.payOrder((List<Drug>) request.getSession().getAttribute(OtherConstant.ORDER_LIST));
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        request.getSession().setAttribute(MessageConstant.PAYMENT_MSG,result);
        request.getSession().setAttribute(OtherConstant.ORDER_LIST,null);
        request.getSession().setAttribute(OtherConstant.ORDER_PRICE, null);

        CommandHelp.sendResponse(request, response);
    }
}
