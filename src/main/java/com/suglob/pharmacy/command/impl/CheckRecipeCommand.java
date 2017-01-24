package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.entity.Client;
import com.suglob.pharmacy.entity.ListDragTag;
import com.suglob.pharmacy.service.DoctorService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.util.ConstantClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class CheckRecipeCommand implements ICommand{
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int userId = Integer.parseInt(request.getParameter(ConstantClass.USER_ID));

        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        DoctorService service = factory.getDoctorService();
        ///////////////////////////////////////////////////////////////////////////////
        Map<String, List> result;
        try {
            result=service.checkRecipe(userId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        //if(!result.get(ConstantClass.DRUGS_NAME_ORDER_RECIPE).isEmpty()){
            List drugsNameOrderRecipe=result.get(ConstantClass.DRUGS_NAME_ORDER_RECIPE);
            List<Client> clientsOrderRecipe=result.get(ConstantClass.CLIENTS_ORDER_RECIPE);
            ListDragTag tag=new ListDragTag(drugsNameOrderRecipe);
            request.getSession().setAttribute(ConstantClass.DRUGS_NAME_ORDER_RECIPE, tag);
            request.getSession().setAttribute(ConstantClass.CLIENTS_ORDER_RECIPE, clientsOrderRecipe);
        //}
        //if(!result.get(ConstantClass.DRUGS_NAME_EXTEND_RECIPE).isEmpty()){
            List drugsNameExtendRecipe=result.get(ConstantClass.DRUGS_NAME_EXTEND_RECIPE);
            List drugsCodeExtendRecipe=result.get(ConstantClass.DRUGS_CODE_EXTEND_RECIPE);
            List<Client> clientsExtendRecipe=result.get(ConstantClass.CLIENTS_EXTEND_RECIPE);
            ListDragTag tag2=new ListDragTag(drugsNameExtendRecipe);
            request.getSession().setAttribute(ConstantClass.DRUGS_NAME_EXTEND_RECIPE, tag2);
            request.getSession().setAttribute(ConstantClass.DRUGS_CODE_EXTEND_RECIPE, drugsCodeExtendRecipe);
            request.getSession().setAttribute(ConstantClass.CLIENTS_EXTEND_RECIPE, clientsExtendRecipe);
        //}

        CommandHelp.sendResponse(request, response);
    }
}