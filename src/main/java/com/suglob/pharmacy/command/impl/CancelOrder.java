package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.utils.CommandHelp;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.utils.ConstantClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CancelOrder implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        List<Drug> orderList = (List<Drug>) request.getSession().getAttribute(ConstantClass.ORDER_LIST);
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        for (Drug drug : orderList) {
            if (!drug.getIsRecipe().equals(ConstantClass.N)){
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

        request.getSession().setAttribute(ConstantClass.ORDER_LIST, null);
        request.getSession().setAttribute(ConstantClass.ORDER_PRICE, null);

        CommandHelp.sendResponse(request, response);
    }
}
