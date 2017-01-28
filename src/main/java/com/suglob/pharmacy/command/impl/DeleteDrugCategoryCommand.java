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
 * This class is for delete drug category.
 */
public class DeleteDrugCategoryCommand implements ICommand {
    /**
     * This method retrieves from request drug category and transmits to the service layer
     * for delete drug category from the database.
     * If there was an error in a parameter, displays it on the page.
     * If the operation is successful, it displays a success message to the screen.
     *
     * @param request for receiving the transmitted data
     * @param response to generate a response
     * @throws CommandException if ServiceException is thrown
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String drugCategory = request.getParameter(OtherConstant.DRUG_CATEGORY);

        PharmacistService service= ServiceFactory.getInstance().getPharmacistService();
        String error;
        try {
            service.deleteDrugCategory(drugCategory);
        }catch (ServiceCheckException e){
            error=e.getMessage().trim();
            request.getSession().setAttribute(OtherConstant.ERROR, error);
            CommandHelp.sendResponse(request, response);
            return;
        }catch (ServiceException e){
            throw new CommandException(e);
        }
        request.getSession().setAttribute(OtherConstant.MSG, MessageConstant.MSG_DELETE_DRUG_CATEGORY_OK);
        CommandHelp.sendResponse(request, response);
    }
}
