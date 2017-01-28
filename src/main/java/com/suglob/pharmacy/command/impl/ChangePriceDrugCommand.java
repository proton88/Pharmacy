package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.service.PharmacistService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceCheckException;
import com.suglob.pharmacy.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This class is for changing price on the drug.
 */
public class ChangePriceDrugCommand implements ICommand {
    /**
     * This method retrieves from request drug parameters and transmits to the service layer
     * for changing price on the drug in the database.
     * If there was an error in a parameter, displays it on the page.
     * If the operation is successful, it displays a success message to the screen.
     *
     * @param request for receiving the transmitted data
     * @param response to generate a response
     * @throws CommandException if ServiceException is thrown
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String drugId=request.getParameter(OtherConstant.DRUG_ID);
        String priceDrug = request.getParameter(OtherConstant.PRICE_DRUG);

        PharmacistService service= ServiceFactory.getInstance().getPharmacistService();
        String error;
        try {
            service.changePriceDrug(drugId, priceDrug);
        } catch (ServiceCheckException e){
            error=e.getMessage().trim();
            request.getSession().setAttribute(OtherConstant.ERROR, error);
            CommandHelp.sendResponse(request, response);
            return;
        }catch (ServiceException e){
            throw new CommandException(e);
        }
        request.getSession().setAttribute(OtherConstant.MSG, MessageConstant.MSG_CHANGE_PRICE_DRUG_OK);
        CommandHelp.sendResponse(request, response);
    }
}
