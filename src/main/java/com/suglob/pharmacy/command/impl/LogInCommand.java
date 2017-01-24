package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.CommonService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.util.ConstantClass;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogInCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        String login=request.getParameter(ConstantClass.LOGIN);
        String password=request.getParameter(ConstantClass.PASSWORD);
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        CommonService service = factory.getCommonService();
        ///////////////////////////////////////////////////////////////////////////////
        User user;
        try{
            user= service.logination(login, password);
        }catch (ServiceException e){
            throw new CommandException(e.toString(), e);
        }

        if (user!=null){
            if (user.getBlock()==1){
                try{
                    request.setAttribute(ConstantClass.ERROR, ConstantClass.BLOCK_USER);
                    request.getRequestDispatcher(ConstantClass.INDEX).forward(request, response);
                }catch(ServletException |IOException e){
                    throw new CommandException("Don't execute index.jsp",e);
                }
                return;
            }
            HttpSession session = request.getSession(true);
            session.setAttribute(ConstantClass.URL,request.getRequestURL());
            session.setAttribute(ConstantClass.USER, user);
            Cookie log=new Cookie(ConstantClass.LOGIN,login);
            log.setMaxAge(ConstantClass.WEEK);
            response.addCookie(log);
            Cookie pass=new Cookie(ConstantClass.PASSWORD,password);
            log.setMaxAge(ConstantClass.WEEK);
            response.addCookie(pass);
            RequestDispatcher dispather=request.getRequestDispatcher(ConstantClass.MAIN);
            try {
                dispather.forward(request, response);
            } catch (ServletException | IOException e) {
                throw new CommandException("Don't execute main.jsp",e);
            }

        }else{
            try{
                request.setAttribute(ConstantClass.ERROR, ConstantClass.NOT_USER);
                request.getRequestDispatcher(ConstantClass.INDEX).forward(request, response);
            }catch(ServletException|IOException e){
                throw new CommandException("Don't execute index.jsp",e);
            }
        }
    }
}
