package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Localization implements ICommand {
    private static final String LOCALE="locale";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String locale=null;
        locale=request.getParameter(LOCALE);

        request.getSession().setAttribute("locale", locale);

        try {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new CommandException("Don't execute index.jsp",e);
        }
    }
}
