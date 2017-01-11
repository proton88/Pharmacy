package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.utils.CommandHelp;
import com.suglob.pharmacy.service.DoctorService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceCheckErrorException;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.utils.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AssignRecipe implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String sDrugId=request.getParameter("drugId");
        String sQuantity=request.getParameter("quantity");
        String sPeriod=request.getParameter("period");
        String sClientId=request.getParameter("clientId");
        String code=request.getParameter("code");
        int clientId=0;
        int drugId=0;
        int quantity=0;
        int period=0;
        if (Validator.checkInteger(sDrugId, sQuantity, sPeriod, sClientId)) {
            clientId = Integer.parseInt(sClientId);
            drugId = Integer.parseInt(sDrugId);
            quantity = Integer.parseInt(sQuantity);
            period = Integer.parseInt(sPeriod);
        }else{
            request.getSession().setAttribute("error", "recipeParameter.wrong");
            CommandHelp.sendResponse(request, response);
            return;
        }

        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        DoctorService service = factory.getDoctorService();
        ///////////////////////////////////////////////////////////////////////////////
        String error="";
        String drugName;
        try {
            drugName=service.assignRecipe(userId,drugId, quantity, period, clientId, code);
        } catch (ServiceCheckErrorException e){
            error=e.getMessage().trim();
            request.getSession().setAttribute("error", error);
            CommandHelp.sendResponse(request, response);
            return;
        }catch (ServiceException e){
            throw new CommandException(e);
        }
        System.out.println(drugName);
        CommandHelp.clearOrderRecipe(request, drugName, clientId);
        CommandHelp.sendResponse(request, response);
    }
}
