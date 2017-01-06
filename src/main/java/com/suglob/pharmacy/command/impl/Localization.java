package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.utils.ConstantClass;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

public class Localization implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String locale=null;
        locale=request.getParameter(ConstantClass.LOCALE);
        request.getSession().setAttribute("locale", locale);

        StringBuffer buf= (StringBuffer) request.getSession().getAttribute("url");
        String url=buf.toString();
        String urlParams = (String) request.getSession().getAttribute("urlParams");

        try {
                response.sendRedirect(url+"?"+urlParams);
            } catch (IOException e) {
                throw new CommandException("Don't execute url: "+url,e);
            }



        /*try {
            request.getRequestDispatcher("main").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
