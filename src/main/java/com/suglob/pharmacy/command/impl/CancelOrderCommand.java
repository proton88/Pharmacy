package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CancelOrderCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        List<Drug> orderList = (List<Drug>) request.getSession().getAttribute(OtherConstant.ORDER_LIST);
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        for (Drug drug : orderList) {
            if (!OtherConstant.N.equals(drug.getIsRecipe())){
                ///////////////////////////////////////////////////////////////////////////////
                ServiceFactory factory = ServiceFactory.getInstance();
                ClientService service = factory.getClientService();
                ///////////////////////////////////////////////////////////////////////////////
                try {
                    service.cancelOrder(drug.getCount(),drug.getId(),Integer.parseInt(drug.getIsRecipe()));
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }
            }
        }

        request.getSession().setAttribute(OtherConstant.ORDER_LIST, null);
        request.getSession().setAttribute(OtherConstant.ORDER_PRICE, null);

        CommandHelp.sendResponse(request, response);
    }
}
