package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.service.DoctorService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceCheckException;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ExtendRecipeCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int positionRecipe=Integer.parseInt(request.getParameter(OtherConstant.POSITION));
        int period;
        if (Validator.checkInteger(request.getParameter(OtherConstant.PERIOD))) {
            period = Integer.parseInt(request.getParameter(OtherConstant.PERIOD));
        }else {
            request.getSession().setAttribute(OtherConstant.ERROR, MessageConstant.WRONG_FORMAT);
            CommandHelp.sendResponse(request, response);
            return;
        }
        List<String> drugsCodeExtendRecipe = (List<String>) request.getSession().getAttribute(OtherConstant.DRUGS_CODE_EXTEND_RECIPE);
        String codeRecipe = drugsCodeExtendRecipe.get(positionRecipe-1);

        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        DoctorService service = factory.getDoctorService();
        ///////////////////////////////////////////////////////////////////////////////
        String error;
        try {
            service.cancelExtendRecipe(codeRecipe);
            service.extendRecipe(period, codeRecipe);
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (ServiceCheckException e) {
            error=e.getMessage().trim();
            request.getSession().setAttribute(OtherConstant.ERROR, error);
            CommandHelp.sendResponse(request, response);
            return;
        }
        CommandHelp.clearExtendRecipe(request, positionRecipe);
        request.getSession().setAttribute(OtherConstant.MSG, MessageConstant.MSG_EXTEND_RECIPE);
        CommandHelp.sendResponse(request, response);
    }
}
