package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.CommonService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.utils.ConstantClass;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Logination implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login=request.getParameter(ConstantClass.LOGIN);
        String password=request.getParameter(ConstantClass.PASSWORD);
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        CommonService service = factory.getCommonService();
        ///////////////////////////////////////////////////////////////////////////////
        User user=null;
        try{
            user= service.logination(login, password);
        }catch (ServiceException e){
            throw new CommandException(e.toString(), e);
        }

        if (user!=null){
            if (user.getBlock()==1){
                try{
                    request.setAttribute("error", "index.error_block");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }catch(ServletException |IOException e){
                    throw new CommandException("Don't execute index.jsp",e);
                }
                return;
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            RequestDispatcher dispather=request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
            try {
                dispather.forward(request, response);
            } catch (ServletException | IOException e) {
                throw new CommandException("Don't execute main.jsp",e);
            }

        }else{
            try{
                request.setAttribute("error", "index.error_not_user");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }catch(ServletException|IOException e){
                throw new CommandException("Don't execute index.jsp",e);
            }
        }
    }
}
