package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.service.DoctorService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceCheckErrorException;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.util.ConstantClass;
import com.suglob.pharmacy.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AssignRecipeCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int userId = Integer.parseInt(request.getParameter(ConstantClass.USER_ID));
        String sDrugId=request.getParameter(ConstantClass.DRUG_ID);
        String sQuantity=request.getParameter(ConstantClass.QUANTITY);
        String sPeriod=request.getParameter(ConstantClass.PERIOD);
        String sClientId=request.getParameter(ConstantClass.CLIENT_ID);
        String code=request.getParameter(ConstantClass.CODE);
        int clientId;
        int drugId;
        int quantity;
        int period;
        if (Validator.checkInteger(sDrugId, sQuantity, sPeriod, sClientId)) {
            clientId = Integer.parseInt(sClientId);
            drugId = Integer.parseInt(sDrugId);
            quantity = Integer.parseInt(sQuantity);
            period = Integer.parseInt(sPeriod);
        }else{
            request.getSession().setAttribute(ConstantClass.ERROR, ConstantClass.MSG_RECIPE_PARAMETER_WRONG);
            CommandHelp.sendResponse(request, response);
            return;
        }

        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        DoctorService service = factory.getDoctorService();
        ///////////////////////////////////////////////////////////////////////////////
        String error;
        String drugName;
        try {
            drugName=service.assignRecipe(userId,drugId, quantity, period, clientId, code);
        } catch (ServiceCheckErrorException e){
            error=e.getMessage().trim();
            request.getSession().setAttribute(ConstantClass.ERROR, error);
            CommandHelp.sendResponse(request, response);
            return;
        }catch (ServiceException e){
            throw new CommandException(e);
        }
        CommandHelp.clearOrderRecipe(request, drugName, clientId);
        CommandHelp.sendResponse(request, response);
    }
}
