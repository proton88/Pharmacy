package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.utils.CommandHelp;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.service.exception.ServiceCheckErrorException;
import com.suglob.pharmacy.utils.ConstantClass;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Registration implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login=request.getParameter(ConstantClass.LOGIN_REG);
        String password=request.getParameter(ConstantClass.PASSWORD_REG);
        String passwordRepeat=request.getParameter(ConstantClass.PASSWORDREPEAT);
        String name=request.getParameter(ConstantClass.NAME);
        String surname=request.getParameter(ConstantClass.SURNAME);
        String patronymic=request.getParameter(ConstantClass.PATRONYMIC);
        String adress=request.getParameter(ConstantClass.ADRESS);
        String passportId=request.getParameter(ConstantClass.PASSPORTID);
        String email=request.getParameter(ConstantClass.EMAIL);
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        ClientService service = factory.getClientService();
        ///////////////////////////////////////////////////////////////////////////////

        String error=ConstantClass.EMPTY_STRING;
        User user1=null;
        try{
            user1= service.registration(login, password, passwordRepeat, name, surname, patronymic, adress, passportId, email);
        }catch (ServiceCheckErrorException e){
            error=e.getMessage().trim();
        }catch (ServiceException e){
            throw new CommandException(e);
        }

        if (user1!=null){
            HttpSession session = request.getSession(true);
            session.setAttribute(ConstantClass.URL,request.getRequestURL());
            session.setAttribute(ConstantClass.USER, user1);
            RequestDispatcher dispather=request.getRequestDispatcher(ConstantClass.MAIN);
            try {
                dispather.forward(request, response);
            } catch (ServletException | IOException e) {
                throw new CommandException("Don't execute main.jsp",e);
            }

        }else{
                if (error.isEmpty()){
                    error=ConstantClass.REG_ELSE;
                }
                request.getSession().setAttribute(ConstantClass.ERROR, error);
                CommandHelp.sendResponse(request, response);
        }

    }
}
