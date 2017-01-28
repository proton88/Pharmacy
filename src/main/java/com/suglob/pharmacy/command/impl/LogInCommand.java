package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.constant.NumberConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.CommonService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * This class is for log in.
 */
public class LogInCommand implements ICommand {
    /**
     * This method retrieves from request user login and password and transmits to the service layer
     * for check client in the database.
     * Also method add user cookies.
     * If there was an error in a parameter, displays it on the page.
     * If the operation is successful, client go to the main page.
     *
     * @param request for receiving the transmitted data
     * @param response to generate a response
     * @throws CommandException if ServiceException is thrown
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        String login=request.getParameter(OtherConstant.LOGIN);
        String password=request.getParameter(OtherConstant.PASSWORD);
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
                    request.setAttribute(OtherConstant.ERROR, MessageConstant.BLOCK_USER);
                    request.getRequestDispatcher(OtherConstant.INDEX).forward(request, response);
                }catch(ServletException |IOException e){
                    throw new CommandException("Don't execute index.jsp",e);
                }
                return;
            }
            HttpSession session = request.getSession(true);
            session.setAttribute(OtherConstant.URL,request.getRequestURL());
            session.setAttribute(OtherConstant.USER, user);
            Cookie log=new Cookie(OtherConstant.LOGIN,login);
            log.setMaxAge(NumberConstant.WEEK);
            response.addCookie(log);
            Cookie pass=new Cookie(OtherConstant.PASSWORD,password);
            pass.setMaxAge(NumberConstant.WEEK);
            response.addCookie(pass);
            RequestDispatcher dispather=request.getRequestDispatcher(OtherConstant.MAIN);
            try {
                dispather.forward(request, response);
            } catch (ServletException | IOException e) {
                throw new CommandException("Don't execute main.jsp",e);
            }

        }else{
            try{
                request.setAttribute(OtherConstant.ERROR, MessageConstant.NOT_USER);
                request.getRequestDispatcher(OtherConstant.INDEX).forward(request, response);
            }catch(ServletException|IOException e){
                throw new CommandException("Don't execute index.jsp",e);
            }
        }
    }
}
