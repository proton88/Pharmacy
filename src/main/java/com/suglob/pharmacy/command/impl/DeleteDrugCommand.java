package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.service.PharmacistService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceCheckErrorException;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.util.ConstantClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteDrugCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String drugId=request.getParameter(ConstantClass.DRUG_ID);

        PharmacistService service= ServiceFactory.getInstance().getPharmacistService();
        String error;
        try {
            service.deleteDrug(drugId);
        } catch (ServiceCheckErrorException e){
            error=e.getMessage().trim();
            request.getSession().setAttribute(ConstantClass.ERROR, error);
            CommandHelp.sendResponse(request, response);
            return;
        }catch (ServiceException e){
            throw new CommandException(e);
        }
        request.getSession().setAttribute(ConstantClass.MSG, ConstantClass.MSG_DELETE_DRUG_OK);
        CommandHelp.sendResponse(request, response);
    }
}
