package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.entity.Client;
import com.suglob.pharmacy.entity.ListDragTag;
import com.suglob.pharmacy.service.DoctorService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
/**
 * This class is for check orders for the recipe.
 */
public class CheckRecipeCommand implements ICommand{
    /**
     * This method retrieves from request user id and transmits to the service layer for check orders for the recipe.
     * Also method create lists ordered recipes.
     *
     * @param request for receiving the transmitted data
     * @param response to generate a response
     * @throws CommandException if ServiceException is thrown
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int userId = Integer.parseInt(request.getParameter(OtherConstant.USER_ID));

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

            List drugsNameOrderRecipe=result.get(OtherConstant.DRUGS_NAME_ORDER_RECIPE);
            List<Client> clientsOrderRecipe=result.get(OtherConstant.CLIENTS_ORDER_RECIPE);
            ListDragTag tag=new ListDragTag(drugsNameOrderRecipe);
            request.getSession().setAttribute(OtherConstant.DRUGS_NAME_ORDER_RECIPE, tag);
            request.getSession().setAttribute(OtherConstant.CLIENTS_ORDER_RECIPE, clientsOrderRecipe);

            List drugsNameExtendRecipe=result.get(OtherConstant.DRUGS_NAME_EXTEND_RECIPE);
            List drugsCodeExtendRecipe=result.get(OtherConstant.DRUGS_CODE_EXTEND_RECIPE);
            List<Client> clientsExtendRecipe=result.get(OtherConstant.CLIENTS_EXTEND_RECIPE);
            ListDragTag tag2=new ListDragTag(drugsNameExtendRecipe);
            request.getSession().setAttribute(OtherConstant.DRUGS_NAME_EXTEND_RECIPE, tag2);
            request.getSession().setAttribute(OtherConstant.DRUGS_CODE_EXTEND_RECIPE, drugsCodeExtendRecipe);
            request.getSession().setAttribute(OtherConstant.CLIENTS_EXTEND_RECIPE, clientsExtendRecipe);


        CommandHelp.sendResponse(request, response);
    }
}
