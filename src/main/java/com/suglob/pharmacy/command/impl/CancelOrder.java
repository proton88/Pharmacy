package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.entity.Drug;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CancelOrder implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        request.getSession().setAttribute("orderList", null);
        request.getSession().setAttribute("orderPrice", null);
        StringBuffer buf= (StringBuffer) request.getSession().getAttribute("url");
        String url=buf.toString();
        String urlParams = (String) request.getSession().getAttribute("urlParams");

        try {
            response.sendRedirect(url+"?"+urlParams);
        } catch (IOException e) {
            throw new CommandException("Don't execute url: "+url,e);
        }
    }
}
