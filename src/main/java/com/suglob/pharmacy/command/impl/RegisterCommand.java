package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.ClientService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.service.exception.ServiceCheckException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login=request.getParameter(OtherConstant.LOGIN_REG);
        String password=request.getParameter(OtherConstant.PASSWORD_REG);
        String passwordRepeat=request.getParameter(OtherConstant.PASSWORDREPEAT);
        String name=request.getParameter(OtherConstant.NAME);
        String surname=request.getParameter(OtherConstant.SURNAME);
        String patronymic=request.getParameter(OtherConstant.PATRONYMIC);
        String adress=request.getParameter(OtherConstant.ADRESS);
        String passportId=request.getParameter(OtherConstant.PASSPORTID);
        String email=request.getParameter(OtherConstant.EMAIL);
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        ClientService service = factory.getClientService();
        ///////////////////////////////////////////////////////////////////////////////

        String error= OtherConstant.EMPTY_STRING;
        User user1=null;
        try{
            user1= service.registration(login, password, passwordRepeat, name, surname, patronymic, adress, passportId, email);
        }catch (ServiceCheckException e){
            error=e.getMessage().trim();
        }catch (ServiceException e){
            throw new CommandException(e);
        }

        if (user1!=null){
            HttpSession session = request.getSession(true);
            session.setAttribute(OtherConstant.URL,request.getRequestURL());
            session.setAttribute(OtherConstant.USER, user1);
            RequestDispatcher dispather=request.getRequestDispatcher(OtherConstant.MAIN);
            try {
                dispather.forward(request, response);
            } catch (ServletException | IOException e) {
                throw new CommandException("Don't execute main.jsp",e);
            }

        }else{
                if (error.isEmpty()){
                    error= MessageConstant.REG_ELSE;
                }
                request.getSession().setAttribute(OtherConstant.ERROR, error);
                CommandHelp.sendResponse(request, response);
        }

    }
}
