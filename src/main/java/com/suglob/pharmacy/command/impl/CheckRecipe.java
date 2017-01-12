package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.utils.CommandHelp;
import com.suglob.pharmacy.entity.Client;
import com.suglob.pharmacy.service.DoctorService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class CheckRecipe implements ICommand{
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int user_id = Integer.parseInt(request.getParameter("user_id"));

        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        DoctorService service = factory.getDoctorService();
        ///////////////////////////////////////////////////////////////////////////////
        Map<String, List> result;
        try {
            result=service.checkRecipe(user_id);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        if(!result.get("drugsNameOrderRecipe").isEmpty()){
            List drugsNameOrderRecipe=result.get("drugsNameOrderRecipe");
            List<Client> clientsOrderRecipe=result.get("clientsOrderRecipe");
            request.getSession().setAttribute("drugsNameOrderRecipe", drugsNameOrderRecipe);
            request.getSession().setAttribute("clientsOrderRecipe", clientsOrderRecipe);
        }
        if(!result.get("drugsNameExtendRecipe").isEmpty()){
            List drugsNameExtendRecipe=result.get("drugsNameExtendRecipe");
            List drugsCodeExtendRecipe=result.get("drugsCodeExtendRecipe");
            List<Client> clientsExtendRecipe=result.get("clientsExtendRecipe");
            request.getSession().setAttribute("drugsNameExtendRecipe", drugsNameExtendRecipe);
            request.getSession().setAttribute("drugsCodeExtendRecipe", drugsCodeExtendRecipe);
            request.getSession().setAttribute("clientsExtendRecipe", clientsExtendRecipe);
        }

        CommandHelp.sendResponse(request, response);

        /*try {
            request.getRequestDispatcher("main").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new CommandException("Don't execute main",e);
        }*/
    }
}
